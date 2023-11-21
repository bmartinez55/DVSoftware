package el.dv.dvproperties.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import el.dv.compose.theme.dvpropeerties.DVPropertiesTheme
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

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

fun String.toCurrencyFormat(): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance(Locale.getDefault())
    return format.format(this.toInt())
}
