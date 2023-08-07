package el.dv.fayucafinder.feature.login.view

sealed class LoginViewEvent {
    object Init : LoginViewEvent()
    object LoginWithGoogleClick : LoginViewEvent()
}

data class LoginViewState(
    val loginState: LoginState = LoginState.Hide
)

sealed class LoginState {
    object Hide : LoginState()
    data class Show(
        val welcomeText: String,
        val signInWithGoogleText: String,
        val signInWithGoogleIcon: Int
    ) : LoginState()
}
