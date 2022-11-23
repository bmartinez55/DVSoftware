package el.dv.fayucafinder.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import el.dv.fayucafinder.databinding.FullScreenComposeLayoutBinding
import el.dv.fayucafinder.extension.inflateComposeContainer
import el.dv.presentation.extension.navigate
import el.dv.presentation.view.effect.ViewEffect
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment holder that determines if user is logged in or not
 */
class FayucaFinderFragment : Fragment() {

    private val viewModel: FayucaFinderVM by viewModel()
    private lateinit var binding: FullScreenComposeLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FullScreenComposeLayoutBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this@FayucaFinderFragment
        }
        setUpView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigate(FayucaFinderFragmentDirections.actionFayucaFinderFragmentToBottomNavigationFragment())
        viewModel.viewEffect.observe(viewLifecycleOwner) { viewEffect ->
            triggerViewEffects(viewEffect)
        }
        // TODO(Uncomment line once login is completely implemented)
        // viewModel.handleEvent(FayucaFinderViewEvent.Init)
    }

    private fun setUpView() {
        inflateComposeContainer(binding.composeView) {
            FayucaFinderScreen()
        }
    }

    private fun triggerViewEffects(viewEffect: ViewEffect) {
        when (viewEffect) {
            is ViewEffect.NavigateToDirection -> navigate(viewEffect.navDirections)
            else -> {}
        }
    }

    companion object {
        const val TAG = "FayucaFinderFragment"
    }
}
