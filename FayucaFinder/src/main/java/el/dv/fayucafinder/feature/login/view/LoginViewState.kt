package el.dv.fayucafinder.feature.login.view

sealed class LoginViewEvent {
    object Init : LoginViewEvent()
}

data class LoginViewState(
    val loginState: LoginState = LoginState.Hide
)

sealed class LoginState {
    object Hide : LoginState()
    object Show : LoginState()
}
