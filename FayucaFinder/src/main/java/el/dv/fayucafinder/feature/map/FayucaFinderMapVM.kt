package el.dv.fayucafinder.feature.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import el.dv.domain.logging.AppLog
import el.dv.presentation.util.AppDictionary
import el.dv.presentation.view.effect.ViewEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FayucaFinderMapVM(
    private val appDictionary: AppDictionary
) : ViewModel() {

    private val internalState = MutableLiveData(getDefaultState())
    private val state: InternalState
        get() = requireNotNull(internalState.value)

    val viewState: LiveData<FayucaFinderMapState> = Transformations.distinctUntilChanged(
        internalState.map {
            it.state
        }
    )

    private val internalViewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> = internalViewEffect

    private val eventChannel = Channel<FayucaFinderMapViewEvent>(Channel.UNLIMITED)

    init {
        eventChannel
            .consumeAsFlow()
            .catch { e ->
                AppLog.e(TAG, "event error", e)
            }
            .onEach { event ->
                AppLog.d(TAG, "event: $event")
                when (event) {
                    is FayucaFinderMapViewEvent.Init -> handleInit(event)
                    is FayucaFinderMapViewEvent.ViewLoaded -> handleViewLoaded(event)
                }
            }
            .launchIn(viewModelScope)
    }

    fun handleEvent(event: FayucaFinderMapViewEvent) {
        eventChannel.trySend(event)
    }

    private fun updateViewState(internalState: InternalState) {
        this.internalState.value = internalState
    }

    private fun sendViewEffect(viewEffect: ViewEffect) {
        internalViewEffect.value = viewEffect
    }

    private fun sendViewEffect(viewEffectList: List<ViewEffect>) {
        viewEffectList.forEach { viewEffect ->
            internalViewEffect.value = viewEffect
        }
    }

    private fun handleInit(event: FayucaFinderMapViewEvent.Init) {
        AppLog.d(TAG, "handleInit")
    }

    private fun handleViewLoaded(event: FayucaFinderMapViewEvent.ViewLoaded) {
        AppLog.d(TAG, "handleViewLoaded")
    }

    private fun getDefaultState(): InternalState {
        return InternalState()
    }

    data class InternalState(
        val state: FayucaFinderMapState = FayucaFinderMapState(),
        val navigationViewReadyState: NavigationViewReadyState = NavigationViewReadyState()
    )

    companion object {
        const val TAG = "FayucaFinderMapVM"
    }
}
