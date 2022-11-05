package el.dv.fayucafinder.feature.map.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import el.dv.domain.navigation.model.MapVisualType
import el.dv.fayucafinder.R
import el.dv.fayucafinder.databinding.MapConfigurationsLayoutBinding
import el.dv.fayucafinder.extension.inflateComposeContainer
import el.dv.presentation.extension.getBundleData
import org.koin.android.ext.android.inject

/**
 * This fragment will hold map configurations to be updated at runtime
 */
class MapConfigurationFragment : BottomSheetDialogFragment() {

    private val viewModel: MapConfigurationVM by inject()
    private lateinit var binding: MapConfigurationsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MapConfigurationBottomSheetDialogAppearance)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MapConfigurationsLayoutBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this@MapConfigurationFragment
            it.vm = viewModel
        }
        viewModel.handleEvent(MapConfigurationViewEvent.Init(getBundleData(MAP_CONFIGURATION_ARGUMENT_KEY, MapVisualType.Normal).value))
        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireDialog().setCancelable(true)
        requireDialog().setCanceledOnTouchOutside(true)
    }

    private fun initView() {
        inflateComposeContainer(binding.composeView) {
            val viewState = viewModel.viewState.observeAsState()

            when (val state = viewState.value?.mapConfigurationState) {
                is MapConfigurationState.Show -> with(binding) {
                    mainConstraintLayout.visibility = View.VISIBLE
                    MapConfigurationScreen(
                        mapTypeTitle = state.mapTypeTitle,
                        currentMapVisualType = state.currentMapVisualType,
                        mapTypeChipList = state.mapTypeChipList,
                        onMapVisualTypeChipClick = { mapVisualType ->
                            viewModel.handleEvent(MapConfigurationViewEvent.MapVisualTypeButtonClick(mapVisualType = mapVisualType))
                        }
                    )
                }
                is MapConfigurationState.Hide -> {}
                else -> {}
            }
        }
    }

    companion object {
        const val TAG = "MapConfigurationFragment"
        const val MAP_CONFIGURATION_ARGUMENT_KEY = "mapvisualtype"
    }
}
