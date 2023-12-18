package el.dv.dvproperties.feature.newproperty.state

import androidx.compose.runtime.MutableState

data class NewPropertyViewState(
    val newPropertyState: NewPropertyState = NewPropertyState.Hide
)

sealed class NewPropertyState {
    object Hide : NewPropertyState()

    data class Show(
        val address: MutableState<String>
    ) : NewPropertyState()
}

sealed class NewPropertyViewEvent {
    object Init : NewPropertyViewEvent()

    object OnBackClick : NewPropertyViewEvent()

    object OnSubmitButtonClick : NewPropertyViewEvent()
}
