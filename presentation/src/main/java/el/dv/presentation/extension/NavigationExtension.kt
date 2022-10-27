package el.dv.presentation.extension

/**
 * ViewModel scope will be tied to navigation graph and
 * ViewModel will be recreated when navigation graph navigates back to root view
 */
// TODO("Uncomment once its needed")
// inline fun <reified VM : ViewModel> Fragment.sharedNavGraphViewModel(
//    @IdRes navGraphId: Int,
//    noinline params: ParametersDefinition? = null
// ): Lazy<VM> {
//    val backStackEntry: NavBackStackEntry by lazy { findNavController().getBackStackEntry(navGraphId) }
//    return lazy(LazyThreadSafetyMode.NONE) {
//        getKoin().getViewModel(
//            owner = { ViewModelOwner(backStackEntry.viewModelStore, backStackEntry) },
//            params = params
//        )
//    }
// }

/**
 * ViewModel scope will be tied to parent navigation graph and
 * ViewModel will be recreated when navigation graph navigates back to root view
 */
// TODO("Uncomment once its needed")
// inline fun <reified VM : ViewModel> Fragment.sharedParentNavGraphViewModel(
//    @IdRes navGraphId: Int,
//    noinline params: ParametersDefinition? = null
// ): Lazy<VM> {
//    val backStackEntry: NavBackStackEntry by lazy { requireParentFragment().requireParentFragment().findNavController().getBackStackEntry(navGraphId) }
//    return lazy(LazyThreadSafetyMode.NONE) {
//        getKoin().getViewModel(
//            owner = { ViewModelOwner(backStackEntry.viewModelStore, backStackEntry)},
//            params = params
//        )
//    }
// }
