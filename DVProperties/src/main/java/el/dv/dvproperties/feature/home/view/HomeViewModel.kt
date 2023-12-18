package el.dv.dvproperties.feature.home.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import el.dv.domain.core.ifFailure
import el.dv.domain.core.ifSuccess
import el.dv.domain.dvproperties.propertydetails.usecase.GetAllOwnedPropertiesUseCase
import el.dv.domain.logging.AppLog
import el.dv.dvproperties.feature.home.state.HomeState
import el.dv.dvproperties.feature.home.state.HomeViewEvent
import el.dv.dvproperties.feature.home.state.HomeViewState
import el.dv.presentation.util.AppDictionary
import el.dv.presentation.view.effect.ViewEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllOwnedPropertiesUseCase: GetAllOwnedPropertiesUseCase,
    private val appDictionary: AppDictionary
) : ViewModel() {

    private val internalState = MutableLiveData(InternalState())
    private val state: InternalState
        get() = requireNotNull(internalState.value)

    val viewState: LiveData<HomeViewState> = internalState.map { it.viewState }.distinctUntilChanged()

    private val internalViewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> = internalViewEffect

    private val eventChannel = Channel<HomeViewEvent>(Channel.UNLIMITED)

    init {
        eventChannel
            .consumeAsFlow()
            .catch { e -> AppLog.d("Event catch $e") }
            .onEach { event ->
                when (event) {
                    is HomeViewEvent.Init -> handleInit(event)
                    is HomeViewEvent.HorizontalGridOnClick -> handleHorizontalGridOnClick(event)
                    is HomeViewEvent.AddPropertyItemOnClick -> handleAddPropertyItemOnClick(event)
                }
            }
            .launchIn(viewModelScope)
    }

    fun handleEvent(event: HomeViewEvent) {
        AppLog.d(TAG, "HomeViewEvent $event")
        eventChannel.trySend(event)
    }

    private fun updateViewState(state: InternalState) {
        AppLog.d(TAG, "updateViewState")
        internalState.value = state
    }

    private fun sendViewEffect(viewEffect: ViewEffect) {
        AppLog.d(TAG, "sendViewEffect")
        internalViewEffect.value = viewEffect
    }

    private fun handleInit(event: HomeViewEvent.Init) {
        AppLog.d(TAG, "handleInit")
        updateViewState(state = state.copy(viewState = state.viewState.copy(homeState = HomeState.Loading)))
        viewModelScope.launch {
            getAllOwnedPropertiesUseCase.run(Unit).ifSuccess { propertyList ->
                updateViewState(
                    state.copy(viewState = state.viewState.copy(homeState = HomeState.Show(propertyList)))
                )
            }.ifFailure { updateViewState(state.copy(viewState = state.viewState.copy(homeState = HomeState.Error))) }
        }
    }

    private fun handleHorizontalGridOnClick(event: HomeViewEvent.HorizontalGridOnClick) {
        AppLog.d(TAG, "handleHorizontalGridOnClick")
    }

    private fun handleAddPropertyItemOnClick(event: HomeViewEvent.AddPropertyItemOnClick) {
        AppLog.d(TAG, "handleAddPropertyItemOnClick")
        sendViewEffect(ViewEffect.NavigateToDirection(HomeFragmentDirections.actionHomeFragmentToNewPropertyFragment()))
    }

    data class InternalState(
        val viewState: HomeViewState = HomeViewState()
    )

    companion object {
        const val TAG = "HomeViewModel"
    }
}
