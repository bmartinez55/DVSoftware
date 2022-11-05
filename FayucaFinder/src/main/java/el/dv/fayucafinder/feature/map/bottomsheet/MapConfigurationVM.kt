package el.dv.fayucafinder.feature.map.bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import el.dv.domain.event.AppEvent
import el.dv.domain.event.EventBus
import el.dv.domain.logging.AppLog
import el.dv.domain.navigation.model.MapVisualType
import el.dv.fayucafinder.feature.map.bottomsheet.viewreducer.GetMapConfigurationInitViewStateViewReducer
import el.dv.presentation.util.AppDictionary
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MapConfigurationVM(
    private val getMapConfigurationInitViewStateViewReducer: GetMapConfigurationInitViewStateViewReducer,
    private val appDictionary: AppDictionary,
    private val eventBus: EventBus
) : ViewModel() {

    private val internalState = MutableLiveData(getDefaultState())
    private val state: InternalState
        get() = requireNotNull(internalState.value)

    val viewState: LiveData<MapConfigurationViewState> = Transformations.distinctUntilChanged(
        internalState.map {
            it.viewState
        }
    )

    private val eventChannel = Channel<MapConfigurationViewEvent>(Channel.UNLIMITED)

    init {
        eventChannel
            .consumeAsFlow()
            .catch { e ->
                AppLog.e(TAG, "event error", e)
            }
            .onEach { event ->
                AppLog.d(TAG, "event: $event")
                when (event) {
                    is MapConfigurationViewEvent.Init -> handleInit(event)
                    is MapConfigurationViewEvent.MapVisualTypeButtonClick -> handleMapVisualTypeButtonClick(event)
                    is MapConfigurationViewEvent.CloseButtonClick -> {}
                }
            }.launchIn(viewModelScope)
    }

    fun handleEvent(event: MapConfigurationViewEvent) {
        eventChannel.trySend(event)
    }

    private fun updateViewState(internalState: InternalState) {
        this.internalState.value = internalState
    }

    private fun handleInit(event: MapConfigurationViewEvent.Init) {
        AppLog.d(TAG, "handleInit")
        val initState = getMapConfigurationInitViewStateViewReducer.run(
            GetMapConfigurationInitViewStateViewReducer.Request(internalState = state, currentMapVisualType = event.mapVisualType)
        )
        updateViewState(initState.state)
    }

    private fun handleMapVisualTypeButtonClick(event: MapConfigurationViewEvent.MapVisualTypeButtonClick) {
        AppLog.d(TAG, "handleMapVisualTypeButtonClick")
        updateViewState(state.copy(currentMapVisualType = event.mapVisualType))
        viewModelScope.launch {
            eventBus.publishEvents(AppEvent.MapVisualTypeChangedReceived(event.mapVisualType))
        }
    }

    private fun getDefaultState(): InternalState {
        return InternalState()
    }

    data class InternalState(
        val viewState: MapConfigurationViewState = MapConfigurationViewState(),
        val currentMapVisualType: MapVisualType = MapVisualType.Normal
    )

    companion object {
        const val TAG = "MapConfigurationVM"
    }
}
