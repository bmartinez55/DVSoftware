package el.dv.dvproperties.core.view.bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import el.dv.dvproperties.databinding.BottomNavigationLayoutBinding
import el.dv.presentation.extension.onBackPress

class BottomNavigationFragment : Fragment() {

    private lateinit var binding: BottomNavigationLayoutBinding
    private lateinit var bottomNavigationNavController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomNavigationLayoutBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val navHost = childFragmentManager.findFragmentById(bottomNavigationContainer.id) as NavHostFragment
            bottomNavigationNavController = navHost.navController
            bottomNavView.setupWithNavController(bottomNavigationNavController)
            bottomNavView.setOnItemReselectedListener { item ->
                val selectedMenuItemNavGraph = navHost.navController.graph.findNode(item.itemId) as NavGraph
                selectedMenuItemNavGraph.let { menuGraph ->
                    navHost.navController.popBackStack(menuGraph.startDestinationId, inclusive = false)
                }
            }
        }
    }
}
