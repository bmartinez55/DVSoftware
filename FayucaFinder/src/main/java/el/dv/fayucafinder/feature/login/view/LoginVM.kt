package el.dv.fayucafinder.feature.login.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import el.dv.domain.logging.AppLog
import el.dv.fayucafinder.R as FayucaFinderR
import el.dv.presentation.R as PresentationR
import el.dv.presentation.util.AppDictionary
import el.dv.presentation.util.AppText
import el.dv.presentation.view.effect.ViewEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginVM(private val appDictionary: AppDictionary) : ViewModel() {

    private val internalState = MutableLiveData(getDefaultState())
    private val state: InternalState
        get() = requireNotNull(internalState.value)

    val viewState: LiveData<LoginViewState> = internalState.map { it.viewState }.distinctUntilChanged()

    private val internalViewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> = internalViewEffect

    private val eventChannel = Channel<LoginViewEvent>(Channel.UNLIMITED)

    init {
        eventChannel
            .consumeAsFlow()
            .catch { e -> AppLog.e(TAG, "event error", e) }
            .onEach { event ->
                AppLog.d(TAG, "event: $event")
                when (event) {
                    is LoginViewEvent.Init -> handleInit(event)
                    is LoginViewEvent.LoginWithGoogleClick -> handleSignInWithGoogleClick(event)
                }
            }.launchIn(viewModelScope)
    }

    fun handleEvent(event: LoginViewEvent) {
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

    private fun handleInit(event: LoginViewEvent.Init) {
        AppLog.d(TAG, "handleInit")
        updateViewState(
            state.copy(
                viewState = state.viewState.copy(
                    loginState = LoginState.Show(
                        welcomeText = appDictionary.toString(AppText.TranslatableText(PresentationR.string.welcome)),
                        signInWithGoogleText = appDictionary.toString(AppText.TranslatableText(FayucaFinderR.string.login_with_google)),
                        signInWithGoogleIcon = PresentationR.drawable.ic_google_icon
                    )
                )
            )
        )
    }

    private fun handleSignInWithGoogleClick(event: LoginViewEvent.LoginWithGoogleClick) {
        AppLog.d(TAG, "handleSignInWithGoogleClick")
        viewModelScope.launch {
            // TODO("Implement Firebase and Google Authentication")
        }
        sendViewEffect(
            ViewEffect.NavigateToDirection(LoginFragmentDirections.actionLoginFragmentToBottomNavigationFragment())
        )
    }

    private fun getDefaultState(): InternalState {
        return InternalState()
    }

    data class InternalState(
        val viewState: LoginViewState = LoginViewState()
    )

    companion object {
        const val TAG = "LoginVM"
    }
}
