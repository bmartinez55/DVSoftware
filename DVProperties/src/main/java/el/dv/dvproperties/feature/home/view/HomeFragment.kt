package el.dv.dvproperties.feature.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.fragment.app.Fragment
import el.dv.domain.logging.AppLog
import el.dv.dvproperties.feature.home.HomeScreenScaffold
import el.dv.dvproperties.feature.home.view.composables.ErrorScreen
import el.dv.dvproperties.feature.home.view.composables.HomeScreen
import el.dv.dvproperties.feature.home.view.composables.LoadingScreen
import el.dv.presentation.extension.requireContentView
import el.dv.presentation.view.effect.ViewEffect
import el.dv.presentation.view.manager.dialog.DialogManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val dialogManager: DialogManager by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = requireContentView {
        val viewstate = viewModel.viewState.observeAsState()
        HomeScreenScaffold(
            topBar = { scaffoldState, scrollState ->
            }
        ) { paddingValues, scaffoldState, scrollState ->
            when (val state = viewstate.value?.homeState) {
                is HomeState.Hide -> {}
                is HomeState.Loading -> LoadingScreen()
                is HomeState.Error -> ErrorScreen { viewModel.handleEvent(HomeViewEvent.Init) }
                is HomeState.Show -> HomeScreen(
                    propertList = state.propertyList,
                    propertyDetailsItemOnClick = { viewModel.handleEvent(HomeViewEvent.HorizontalGridOnClick(it)) },
                    addPropertyDetailsItemOnClick = { viewModel.handleEvent(HomeViewEvent.AddPropertyItemOnClick) }
                )
                else -> {}
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewEffect.observe(viewLifecycleOwner) { viewEffect ->
            triggerViewEffect(viewEffect)
        }

        viewModel.handleEvent(HomeViewEvent.Init)
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
