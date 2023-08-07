package el.dv.fayucafinder.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import el.dv.compose_uikit.extension.requireContentView
import el.dv.compose_uikit.theme.fayucafinder.FayucaFinderTheme
import el.dv.presentation.extension.navigate
import el.dv.presentation.view.effect.ViewEffect
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment holder that determines if user is logged in or not
 */
class FayucaFinderFragment : Fragment() {

    private val viewModel: FayucaFinderVM by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        this.requireContentView(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)) {
            FayucaFinderTheme {
                FayucaFinderScreen()
            }
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
