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
import el.dv.fayucafinder.R
import el.dv.fayucafinder.databinding.FayucaFinderMapLayoutBinding
import el.dv.presentation.extension.onBackPress
import el.dv.presentation.permission.Permission
import el.dv.presentation.view.effect.ViewEffect
import el.dv.presentation.view.manager.dialog.DialogManager
import el.dv.presentation.view.manager.notification.NotificationManager
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FayucaFinderMapFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: FayucaFinderMapVM by viewModel()
    private val navigationMapFactory: NavigationMapFactory<GoogleMap, Marker> by inject()
    private lateinit var navigationMap: NavigationMap<GoogleMap, Marker>
    private val dialogManager: DialogManager by inject()
    private val notificationManager: NotificationManager by inject()
    private lateinit var binding: FayucaFinderMapLayoutBinding
    private val fayucaFinderMapLifecycleObserver: FayucaFinderMapLifecycleObserver = get {
        parametersOf(
            this,
            {},
            {}
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FayucaFinderMapLayoutBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this@FayucaFinderMapFragment
            it.vm = viewModel
        }

        ((childFragmentManager.findFragmentById(R.id.map_container)) as SupportMapFragment).let {
            it.getMapAsync(this)
        }

        onBackPress(false) {
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
        viewModel.viewEffect.observe(viewLifecycleOwner) { viewEffect ->
            triggerViewEffect(viewEffect)
        }
    }

    private fun triggerViewEffect(viewEffect: ViewEffect) {
        when (viewEffect) {
            is ViewEffect.Default -> {}
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
    }
}
