package el.dv.presentation.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.java.KoinJavaComponent.getKoin

/**
 * ViewModel scope will be tied to navigation graph and
 * ViewModel will be recreated when navigation graph navigates back to root view
 */
// TODO("Uncomment once its needed")
inline fun <reified VM : ViewModel> Fragment.sharedNavGraphViewModel(
    @IdRes navGraphId: Int,
    noinline parameters: ParametersDefinition? = null
): Lazy<VM> {
    val backStackEntry: NavBackStackEntry by lazy { findNavController().getBackStackEntry(navGraphId) }
    return lazy(LazyThreadSafetyMode.NONE) {
        getKoin().getViewModel(
            owner = { ViewModelOwner(backStackEntry.viewModelStore, backStackEntry) },
            parameters = parameters
        )
    }
}

/**
 * ViewModel scope will be tied to parent navigation graph and
 * ViewModel will be recreated when navigation graph navigates back to root view
 */
// TODO("Uncomment once its needed")
inline fun <reified VM : ViewModel> Fragment.sharedParentNavGraphViewModel(
    @IdRes navGraphId: Int,
    noinline parameters: ParametersDefinition? = null
): Lazy<VM> {
    val backStackEntry: NavBackStackEntry by lazy { requireParentFragment().requireParentFragment().findNavController().getBackStackEntry(navGraphId) }
    return lazy(LazyThreadSafetyMode.NONE) {
        getKoin().getViewModel(
            owner = { ViewModelOwner(backStackEntry.viewModelStore, backStackEntry) },
            parameters = parameters
        )
    }
}
