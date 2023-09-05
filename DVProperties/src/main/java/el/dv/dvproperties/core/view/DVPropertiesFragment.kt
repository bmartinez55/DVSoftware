package el.dv.dvproperties.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import el.dv.compose_uikit.theme.dvpropeerties.DVPropertiesTheme
import el.dv.presentation.extension.requireContentView

class DVPropertiesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = requireContentView {
        DVPropertiesTheme {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().navigate(DVPropertiesFragmentDirections.actionDVPropertiesFragmentToBottomNavigationFragment())
    }

    companion object {
        const val TAG = "DVPropertiesFragment"
    }
}
