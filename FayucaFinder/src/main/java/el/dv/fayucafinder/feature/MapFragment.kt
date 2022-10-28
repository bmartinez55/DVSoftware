package el.dv.fayucafinder.feature

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
import el.dv.fayucafinder.databinding.MapLayoutBinding
import org.koin.android.ext.android.inject

/**
 * Fragment that host the one feature
 */
class MapFragment : Fragment(), OnMapReadyCallback {

    private val navigationMapFactory: NavigationMapFactory<GoogleMap, Marker> by inject()
    private lateinit var navigationMap: NavigationMap<GoogleMap, Marker>
    private lateinit var binding: MapLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MapLayoutBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this@MapFragment
        }

        ((childFragmentManager.findFragmentById(R.id.map_container)) as SupportMapFragment).let {
            it.getMapAsync(this)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMapReady(map: GoogleMap) {
        AppLog.d(TAG, "onMapReady")
        context?.let { context ->
            map.let { googleMap ->
                navigationMap = navigationMapFactory.getNavigationMap(googleMap)
                MapsInitializer.initialize(context)
            }
        }
    }

    companion object {
        const val TAG = "MapFragment"
    }
}
