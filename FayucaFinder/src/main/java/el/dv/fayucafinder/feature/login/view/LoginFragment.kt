package el.dv.fayucafinder.feature.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.input.TextFieldValue
import androidx.fragment.app.Fragment
import el.dv.compose_uikit.extension.requireContentView
import el.dv.compose_uikit.theme.fayucafinder.FayucaFinderTheme
import el.dv.presentation.extension.navigate
import el.dv.presentation.extension.onBackPress
import el.dv.presentation.view.effect.ViewEffect
import el.dv.presentation.view.manager.dialog.DialogManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginVM by viewModel()
    private val dialogManager: DialogManager by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        onBackPress(false) { activity?.onBackPressedDispatcher?.onBackPressed() }

        viewModel.handleEvent(LoginViewEvent.Init)

        return this.requireContentView(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)) {
            FayucaFinderTheme {
                val viewState = viewModel.viewState.observeAsState()
                when (val state = viewState.value?.loginState) {
                    is LoginState.Show -> LoginScreen(
                        welcomeText = state.welcomeText,
                        signInWithGoogleText = state.signInWithGoogleText,
                        signInWithGoogleButtonIcon = state.signInWithGoogleIcon,
                        onSignInWithGoogleClick = {
                            viewModel.handleEvent(LoginViewEvent.LoginWithGoogleClick)
                        }
                    )
                    is LoginState.Hide -> {}
                    else -> {}
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewEffect.observe(viewLifecycleOwner) { viewEffect ->
            triggerViewEffect(viewEffect)
        }
    }

    private fun triggerViewEffect(viewEffect: ViewEffect) {
        when (viewEffect) {
            is ViewEffect.ShowDialogEffect -> dialogManager.showDialog(
                context = viewEffect.context,
                title = viewEffect.title,
                message = viewEffect.message,
                positiveButtonTitle = viewEffect.positiveButtonTitle,
                positiveClickListener = viewEffect.positiveClickListener,
                negativeButtonTitle = viewEffect.negativeButtonTitle,
                negativeClickListener = viewEffect.negativeClickListener,
                onKeyListener = viewEffect.onKeyListener
            )
            is ViewEffect.DismissDialogEffect -> dialogManager.dismiss()
            is ViewEffect.NavigateToDirection -> navigate(viewEffect.navDirections)
            else -> {}
        }
    }

    companion object {
        const val TAG = "LoginFragment"
    }
}
