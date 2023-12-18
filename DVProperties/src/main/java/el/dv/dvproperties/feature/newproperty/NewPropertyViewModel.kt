package el.dv.dvproperties.feature.newproperty

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import el.dv.domain.dvproperties.propertydetails.usecase.AddNewPropertyUseCase
import el.dv.domain.logging.AppLog
import el.dv.dvproperties.feature.newproperty.state.NewPropertyState
import el.dv.dvproperties.feature.newproperty.state.NewPropertyViewEvent
import el.dv.dvproperties.feature.newproperty.state.NewPropertyViewState
import el.dv.presentation.util.AppDictionary
import el.dv.presentation.view.effect.ViewEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NewPropertyViewModel(
    private val addNewPropertyUseCase: AddNewPropertyUseCase,
    private val appDictionary: AppDictionary
) : ViewModel() {

    private val internalState = MutableLiveData(InternalState())
    private val state: InternalState
        get() = requireNotNull(internalState.value)

    val viewState: LiveData<NewPropertyViewState> = internalState.map { it.viewState }.distinctUntilChanged()

    private val internalViewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> = internalViewEffect

    private val eventChannel = Channel<NewPropertyViewEvent>(Channel.UNLIMITED)

    val addressState: MutableState<String> = mutableStateOf("")

    init {
        eventChannel
            .consumeAsFlow()
            .catch { e -> AppLog.d("Event catch $e") }
            .onEach { event ->
                when (event) {
                    is NewPropertyViewEvent.Init -> handleInit(event)
                    is NewPropertyViewEvent.OnBackClick -> handleOnBackClick(event)
                    is NewPropertyViewEvent.OnSubmitButtonClick -> handleOnSubmitButtonClick(event)
                }
            }.launchIn(viewModelScope)
    }

    fun handleViewEvent(event: NewPropertyViewEvent) {
        AppLog.d(TAG, "handleViewEvent $event")
        eventChannel.trySend(event)
    }

    private fun sendViewEffect(viewEffect: ViewEffect) {
        AppLog.d(TAG, "sendViewEffect")
        internalViewEffect.value = viewEffect
    }

    private fun updateViewState(state: InternalState) {
        internalState.value = state
    }

    private fun handleInit(event: NewPropertyViewEvent.Init) {
        AppLog.d(TAG, "handleInit")
        updateViewState(state.copy(viewState = state.viewState.copy(newPropertyState = NewPropertyState.Show(addressState))))
    }

    private fun handleOnBackClick(event: NewPropertyViewEvent.OnBackClick) {
        AppLog.d(TAG, "handleOnBackClick")
        sendViewEffect(ViewEffect.NavigateBack)
    }

    private fun handleOnSubmitButtonClick(event: NewPropertyViewEvent.OnSubmitButtonClick) {
        AppLog.d(TAG, "handleOnSubmitButtonClick")
    }

    data class InternalState(
        val viewState: NewPropertyViewState = NewPropertyViewState()
    )

    companion object {
        const val TAG = "NewPropertyViewModel"
    }
}
