package el.dv.dvproperties.feature.propertydetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.livedata.observeAsState
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import el.dv.compose.theme.dvpropeerties.DVPropertiesTheme
import el.dv.compose.widgets.PleaseRetryFullScreen
import el.dv.domain.logging.AppLog
import el.dv.dvproperties.feature.propertydetails.composables.PropertyDetailsScreen
import el.dv.dvproperties.feature.propertydetails.state.PropertyDetailsViewEvents
import el.dv.dvproperties.feature.propertydetails.state.PropertyDetailsViewState
import el.dv.presentation.extension.onBackPress
import el.dv.presentation.extension.requireContentView
import el.dv.presentation.view.effect.ViewEffect
import el.dv.presentation.view.manager.dialog.DialogManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PropertyDetailsFragment : Fragment() {
    private val viewModel: PropertyDetailsViewModel by viewModel()
    private val dialogManager: DialogManager by inject()
    private lateinit var binding: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = requireContentView {
        DVPropertiesTheme {
            val viewState = viewModel.viewState.observeAsState()
            val scrollState = rememberScrollState()

            when (val state = viewState.value?.propertyDetailsViewState) {
                is PropertyDetailsViewState.Hide -> {}
                is PropertyDetailsViewState.Error -> PleaseRetryFullScreen()
                is PropertyDetailsViewState.Show -> PropertyDetailsScreen(scrollState, state.propertyDetails)
                else -> {}
            }
        }
    }.apply {
        binding = this.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPress(true) {
            viewModel.handleViewEvent(PropertyDetailsViewEvents.OnBackPress)
        }
        viewModel.viewEffect.observe(viewLifecycleOwner) { viewEffect ->
            AppLog.d(TAG, "viewEffect: $viewEffect")
            triggerViewEffects(viewEffect)
        }
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
        const val TAG = "PropertyDetailsFragment"
    }
}
