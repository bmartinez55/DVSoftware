package el.dv.dvproperties.feature.newproperty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import el.dv.compose.widgets.DVTopBar
import el.dv.domain.logging.AppLog
import el.dv.presentation.R
import el.dv.dvproperties.feature.newproperty.composables.NewPropertyScaffold
import el.dv.dvproperties.feature.newproperty.composables.NewPropertyScreen
import el.dv.dvproperties.feature.newproperty.state.NewPropertyState
import el.dv.dvproperties.feature.newproperty.state.NewPropertyViewEvent
import el.dv.presentation.extension.onBackPress
import el.dv.presentation.extension.requireContentView
import el.dv.presentation.view.effect.ViewEffect
import el.dv.presentation.view.manager.dialog.DialogManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewPropertyFragment : Fragment() {

    private val viewModel: NewPropertyViewModel by viewModel()
    private val dialogManager: DialogManager by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = requireContentView {
        val viewState = viewModel.viewState.observeAsState()

        NewPropertyScaffold(
            topBar = { scaffoldState, scrollState ->
                DVTopBar(scaffoldState = scaffoldState, scrollState = scrollState, title = stringResource(id = R.string.back)) {
                    viewModel.handleViewEvent(NewPropertyViewEvent.OnBackClick)
                }
            }
        ) { _, _, scrollState ->
            when (val state = viewState.value?.newPropertyState) {
                is NewPropertyState.Hide -> {}
                is NewPropertyState.Show -> NewPropertyScreen(
                    scrollState = scrollState,
                    address = state.address,
                    bedroomDropDownClick = {

                    },
                    bathroomDropDownClick = {

                    },
                    onSubmitClick = {
                        viewModel.handleViewEvent(NewPropertyViewEvent.OnSubmitButtonClick)
                    }
                )
                else -> {}
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPress(true) {
            viewModel.handleViewEvent(NewPropertyViewEvent.OnBackClick)
        }
        viewModel.viewEffect.observe(viewLifecycleOwner) { viewEffect ->
            AppLog.d(TAG, "viewEffect $viewEffect")
            triggerViewEffects(viewEffect)
        }
        viewModel.handleViewEvent(NewPropertyViewEvent.Init)
    }

    private fun triggerViewEffects(viewEffect: ViewEffect) {
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
            is ViewEffect.NavigateBack -> findNavController().navigateUp()
            else -> {}
        }
    }
    companion object {
        const val TAG = "NewPropertyFragment"
    }
}
