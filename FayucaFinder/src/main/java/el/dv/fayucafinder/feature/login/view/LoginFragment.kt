package el.dv.fayucafinder.feature.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.fragment.app.Fragment
import el.dv.fayucafinder.databinding.FullScreenComposeLayoutBinding
import el.dv.fayucafinder.extension.inflateComposeContainer
import el.dv.presentation.extension.onBackPress
import el.dv.presentation.view.effect.ViewEffect
import el.dv.presentation.view.manager.dialog.DialogManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginVM by viewModel()
    private val dialogManager: DialogManager by inject()
    private lateinit var binding: FullScreenComposeLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FullScreenComposeLayoutBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this@LoginFragment
        }

        onBackPress(false) {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        viewModel.handleEvent(LoginViewEvent.Init)
        renderViewState()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewEffect.observe(viewLifecycleOwner) { viewEffect ->
            triggerViewEffect(viewEffect)
        }
    }

    private fun renderViewState() {
        inflateComposeContainer(binding.composeView) {
            val viewState = viewModel.viewState.observeAsState()
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
            else -> {}
        }
    }

    companion object {
        const val TAG = "LoginFragment"
    }
}
