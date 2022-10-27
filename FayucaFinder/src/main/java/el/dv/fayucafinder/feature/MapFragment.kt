package el.dv.fayucafinder.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import el.dv.fayucafinder.databinding.MapLayoutBinding

/**
 * Fragment that host the one feature
 */
class MapFragment : Fragment() {

    private lateinit var binding: MapLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MapLayoutBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this@MapFragment
        }

//        binding.composeView.setContent {
//            Column(modifier = Modifier.fillMaxSize()) {
//                Text(text = "Map Fragment")
//            }
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
