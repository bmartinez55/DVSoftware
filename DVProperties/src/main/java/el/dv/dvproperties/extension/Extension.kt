package el.dv.dvproperties.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment

fun Fragment.inflateComposeContainer(view: ComposeView, content: @Composable () -> Unit) {
    view.apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            // TODO("Add Theme for DVProperties App")
        }
    }
}
