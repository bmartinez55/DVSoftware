package el.dv.dvproperties.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import el.dv.compose_uikit.theme.dvpropeerties.DVPropertiesTheme

fun Fragment.inflateComposeContainer(view: ComposeView, content: @Composable () -> Unit) {
    view.apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            DVPropertiesTheme {
                content()
            }
        }
    }
}
