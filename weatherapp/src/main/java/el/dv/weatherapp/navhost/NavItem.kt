package el.dv.weatherapp.navhost

sealed class NavItem(val route: String) {
    data object Home : NavItem(Screens.HOME.name)
    data object Search : NavItem(Screens.SEARCH.name)
}
