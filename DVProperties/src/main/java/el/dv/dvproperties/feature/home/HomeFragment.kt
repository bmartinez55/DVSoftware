package el.dv.dvproperties.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.livedata.observeAsState
import androidx.fragment.app.Fragment
import el.dv.domain.logging.AppLog
import el.dv.presentation.extension.requireContentView
import el.dv.presentation.view.effect.ViewEffect
import el.dv.presentation.view.manager.dialog.DialogManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val dialogManager: DialogManager by inject()

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = requireContentView {
        val state = viewModel.viewState.observeAsState()
        HomeScreenScaffold(
            topBar = { scaffoldState, scrollState ->

            }
        ) { paddingValues, scaffoldState, scrollState ->
            when (val viewState = state.value?.horizontalGridState) {
                is HorizontalGridState.Show -> PriorityHorizontalGrid(propertyList = viewState.propertyList)
                is HorizontalGridState.Hide -> {}
                else -> {}
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
        AppLog.d(TAG, "viewEffect: $viewEffect")

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
        const val TAG = "HomeFragment"
    }
}
