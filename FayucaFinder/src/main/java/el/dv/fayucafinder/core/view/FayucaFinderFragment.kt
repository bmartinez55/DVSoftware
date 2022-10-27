package el.dv.fayucafinder.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import el.dv.fayucafinder.databinding.FayucaFinderLayoutBinding

/**
 * Fragment holder that determines if user is logged in or not
 */
class FayucaFinderFragment : Fragment() {

    private lateinit var binding: FayucaFinderLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FayucaFinderLayoutBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this@FayucaFinderFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().navigate(FayucaFinderFragmentDirections.actionFayucaFinderFragmentToBottomNavigationFragment())
    }

    companion object {
        const val TAG = "FayucaFinderFragment"
    }
}