package el.dv.dvproperties.feature.propertydetails.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import el.dv.domain.core.ifFailure
import el.dv.domain.core.ifSuccess
import el.dv.domain.dvproperties.propertydetails.model.PropertyDetails
import el.dv.domain.dvproperties.propertydetails.usecase.GetPropertyByIdUseCase
import el.dv.domain.logging.AppLog
import el.dv.dvproperties.feature.propertydetails.state.PropertyDetailsState
import el.dv.dvproperties.feature.propertydetails.state.PropertyDetailsViewEvents
import el.dv.dvproperties.feature.propertydetails.state.PropertyDetailsViewState
import el.dv.dvproperties.feature.propertydetails.view.viewreducer.GetPropertyDetailsViewReducer
import el.dv.presentation.util.AppDictionary
import el.dv.presentation.view.effect.ViewEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PropertyDetailsViewModel(
    private val getPropertyByIdUseCase: GetPropertyByIdUseCase,
    private val getPropertyDetailsViewReducer: GetPropertyDetailsViewReducer
) : ViewModel() {

    private val internalState = MutableLiveData(InternalState())
    private val state: InternalState
        get() = requireNotNull(internalState.value)

    val viewState: LiveData<PropertyDetailsState> = internalState.map { it.viewState }.distinctUntilChanged()

    private val internalViewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> = internalViewEffect

    private val eventChannel = Channel<PropertyDetailsViewEvents>(Channel.UNLIMITED)

    init {
        eventChannel
            .consumeAsFlow()
            .catch { e -> AppLog.e(TAG, "Event catch $e") }
            .onEach { event ->
                when (event) {
                    is PropertyDetailsViewEvents.Init -> handleInit(event)
                    is PropertyDetailsViewEvents.OnBackPress -> handleOnBackPress(event)
                }
            }.launchIn(viewModelScope)
    }

    fun handleViewEvent(event: PropertyDetailsViewEvents) {
        AppLog.d(TAG, "handleViewEvent")
        eventChannel.trySend(event)
    }

    private fun sendViewEffect(viewEffect: ViewEffect) {
        AppLog.d(TAG, "sendViewEffect")
        internalViewEffect.value = viewEffect
    }

    private fun updateViewState(state: InternalState) {
        internalState.value = state
    }

    private fun handleInit(event: PropertyDetailsViewEvents.Init) {
        AppLog.d(TAG, "handleInit")
        viewModelScope.launch {
            getPropertyByIdUseCase.run(event.propertyId).ifSuccess { propertyDetails ->
                showPropertyDetailsView(propertyDetails)
            }.ifFailure { e ->
                AppLog.e(TAG, "Error GetPropertyById: $e")
                showErrorView()
            }
        }
    }

    private fun showPropertyDetailsView(propertyDetails: PropertyDetails) {
        AppLog.d(TAG, "sendPropertyDetailsView")
        updateViewState(
            getPropertyDetailsViewReducer.run(GetPropertyDetailsViewReducer.Request(state, propertyDetails)).state
        )
    }

    private fun showErrorView() {
        AppLog.d(TAG, "showErrorView")
        updateViewState(state.copy(viewState = state.viewState.copy(propertyDetailsViewState = PropertyDetailsViewState.Error)))
    }

    private fun handleOnBackPress(event: PropertyDetailsViewEvents.OnBackPress) {
        AppLog.d(TAG, "handleOnBackPress")
        sendViewEffect(ViewEffect.NavigateBack)
    }

    data class InternalState(
        val viewState: PropertyDetailsState = PropertyDetailsState()
    )

    companion object {
        const val TAG = "PropertyDetailsViewModel"
    }
}
