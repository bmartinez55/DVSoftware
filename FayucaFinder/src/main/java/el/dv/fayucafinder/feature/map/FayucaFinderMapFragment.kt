package el.dv.fayucafinder.feature.map

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import el.dv.domain.logging.AppLog
import el.dv.domain.navigation.NavigationMap
import el.dv.domain.navigation.NavigationMapFactory
import el.dv.domain.navigation.model.MapVisualType
import el.dv.fayucafinder.R
import el.dv.fayucafinder.databinding.FayucaFinderMapLayoutBinding
import el.dv.fayucafinder.feature.map.bottomsheet.MapConfigurationFragment
import el.dv.presentation.extension.convertParcelableToBundle
import el.dv.presentation.extension.navigate
import el.dv.presentation.extension.onBackPress
import el.dv.presentation.extension.sharedNavGraphViewModel
import el.dv.presentation.permission.Permission
import el.dv.presentation.view.effect.ViewEffect
import el.dv.presentation.view.manager.dialog.DialogManager
import el.dv.presentation.view.manager.notification.NotificationManager
import el.dv.presentation.view.state.NavigationMapCenter
import el.dv.presentation.view.state.NavigationMapState
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class FayucaFinderMapFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: FayucaFinderMapVM by sharedNavGraphViewModel(R.id.bottom_nav_graph)
    private val navigationMapFactory: NavigationMapFactory<GoogleMap, Marker> by inject()
    private lateinit var navigationMap: NavigationMap<GoogleMap, Marker>
    private val dialogManager: DialogManager by inject()
    private val notificationManager: NotificationManager by inject()
    private lateinit var binding: FayucaFinderMapLayoutBinding
    private val fayucaFinderMapLifecycleObserver: FayucaFinderMapLifecycleObserver = get {
        parametersOf(
            this,
            { viewModel.handleEvent(FayucaFinderMapViewEvent.GetLocation) },
            { viewModel.handleEvent(FayucaFinderMapViewEvent.StopLocation) }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FayucaFinderMapLayoutBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this@FayucaFinderMapFragment
            it.vm = viewModel
            it.currentLocationView.vm = viewModel
            it.mapConfigurationButtonView.vm = viewModel
            it.bottomBannerView.vm = viewModel
        }

        ((childFragmentManager.findFragmentById(R.id.map_container)) as SupportMapFragment).let {
            it.getMapAsync(this)
        }

        onBackPress(false) {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        viewModel.handleEvent(
            FayucaFinderMapViewEvent.Init(
                permission = Permission(permissionId = Manifest.permission.ACCESS_FINE_LOCATION),
                fragment = this
            )
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            AppLog.d(TAG, "ViewState: $viewState")
            renderNavigationMapState(viewState.navigationMapState)
            renderCurrentLocationMenuState(viewState.currentLocationMenuState)
            renderMapConfigurationMenuState(viewState.mapConfigurationMenuState)
            renderBottomBannerViewState(viewState.bottomBannerViewState)
        }
        viewModel.viewEffect.observe(viewLifecycleOwner) { viewEffect ->
            AppLog.d(TAG, "ViewEffect: $viewEffect")
            triggerViewEffect(viewEffect)
        }
    }

    private fun renderNavigationMapState(viewState: NavigationMapState) {
        if (!::navigationMap.isInitialized) {
            AppLog.e(TAG, "renderNavigationMapState called when google map is not initialized")
            return
        }

        when (viewState) {
            is NavigationMapState.Init -> {}
            is NavigationMapState.Idle -> {}
            is NavigationMapState.Hide -> with(binding) {
                mapContainer.visibility = View.GONE
            }
            is NavigationMapState.Show -> with(binding) {
                mapContainer.visibility = View.VISIBLE
                navigationMap.setZoomGestureEnabled(viewState.mapFeature.zoomGestureEnabled)
                navigationMap.setRotationGestureEnabled(viewState.mapFeature.rotationGestureEnabled)
                navigationMap.setTiltGestureEnabled(viewState.mapFeature.tiltGestureEnabled)
                navigationMap.setIndoorEnabled(viewState.mapFeature.indoorEnabled)
                navigationMap.setTrafficEnabled(viewState.mapFeature.trafficEnabled)
                navigationMap.setUserLocationEnabled(viewState.mapFeature.userLocationEnabled)
                navigationMap.setMapToolbarEnabled(viewState.mapFeature.mapToolbarEnabled)
                navigationMap.setMapInteractionListener(viewState.interactionListener, viewState.interactionFilterLogic)
                when (viewState.mapVisualType) {
                    MapVisualType.ThreeDimension -> navigationMap.setBuildingEnabled(viewState.mapFeature.buildingEnabled)
                    else -> navigationMap.setMapType(viewState.mapVisualType)
                }
                navigationMap.setMapType(viewState.mapVisualType)
                when (val navigationMapCenter = viewState.navigationMapCenter) {
                    is NavigationMapCenter.Unbounded -> navigationMap.setMapCenterLocation(
                        location = navigationMapCenter.centerLocation,
                        zoomLevel = navigationMapCenter.zoomLevel,
                        animate = navigationMapCenter.animate
                    )
                    is NavigationMapCenter.Bounded -> navigationMap.setMapCenterLocation(
                        coordinateList = navigationMapCenter.coordinateList,
                        boundingBoxPadding = navigationMapCenter.boundBoxPadding,
                        animate = navigationMapCenter.animate
                    )
                }
            }
            is NavigationMapState.UpdateCenterLocation -> {
                when (val navigationMapCenter = viewState.navigationMapCenter) {
                    is NavigationMapCenter.Unbounded -> navigationMap.setMapCenterLocation(
                        location = navigationMapCenter.centerLocation,
                        zoomLevel = navigationMapCenter.zoomLevel,
                        animate = navigationMapCenter.animate,
                        tilt = navigationMapCenter.tilt
                    )
                    is NavigationMapCenter.Bounded -> navigationMap.setMapCenterLocation(
                        coordinateList = navigationMapCenter.coordinateList,
                        boundingBoxPadding = navigationMapCenter.boundBoxPadding,
                        animate = navigationMapCenter.animate
                    )
                }
                navigationMap.setUserLocationEnabled(viewState.showCurrentLocation)
            }
        }
    }

    private fun renderCurrentLocationMenuState(viewState: CurrentLocationMenuState) {
        when (viewState) {
            is CurrentLocationMenuState.Hide -> with(binding) {
                currentLocationButtonConstraintLayout.visibility = View.GONE
            }
            is CurrentLocationMenuState.Show -> with(binding) {
                currentLocationButtonConstraintLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun renderMapConfigurationMenuState(viewState: MapConfigurationMenuState) {
        when (viewState) {
            is MapConfigurationMenuState.Hide -> with(binding) {
                mapConfigurationButtonConstraintLayout.visibility = View.GONE
            }
            is MapConfigurationMenuState.Show -> with(binding) {
                mapConfigurationButtonConstraintLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun renderBottomBannerViewState(viewState: BottomBannerViewState) {
        when (viewState) {
            is BottomBannerViewState.Hide -> with(binding) {
                bottomBannerConstraintLayout.visibility = View.GONE
            }
            is BottomBannerViewState.ShowWifiDisconnected -> with(binding) {
                bottomBannerConstraintLayout.visibility = View.VISIBLE
                bottomBannerView.bottomBannerCardView.visibility = View.VISIBLE
                bottomBannerView.wifiDisconnectedBanner.visibility = View.VISIBLE
                bottomBannerView.bottomBannerTitle.text = viewState.title
            }
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
            is ViewEffect.StartActivityEffect -> activity?.startActivity(viewEffect.intent)
            is ViewEffect.NavigateToGlobalActionEffect -> navigate(viewEffect.actionId)
            is ViewEffect.UpdateMapTypeEffect -> when (viewEffect.mapVisualType) {
                MapVisualType.ThreeDimension -> navigationMap.setBuildingEnabled(enable = true)
                else -> {
                    navigationMap.setMapType(viewEffect.mapVisualType)
                    navigationMap.setBuildingEnabled(false)
                }
            }
            is ViewEffect.ShowMapConfigurationsScreenEffect -> {
                val fm = requireActivity().supportFragmentManager.beginTransaction()
                MapConfigurationFragment().apply {
                    show(fm, null)
                    arguments = viewEffect.mapVisualType.convertParcelableToBundle(MAP_CONFIGURATION_ARGUMENT_KEY)
                }
            }
            else -> {}
        }
    }

    override fun onMapReady(map: GoogleMap) {
        AppLog.d(TAG, "onMapReady")
        context?.let { context ->
            map.let { googleMap ->
                navigationMap = navigationMapFactory.getNavigationMap(googleMap)
                MapsInitializer.initialize(context)
                viewModel.handleEvent(FayucaFinderMapViewEvent.ViewLoaded)
            }
        }
    }

    companion object {
        const val TAG = "MapFragment"
        const val MAP_CONFIGURATION_ARGUMENT_KEY = "mapvisualtype"
    }
}
