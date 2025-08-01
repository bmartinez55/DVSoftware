package el.dv.data.extension

import java.text.NumberFormat
import java.util.Locale

fun Int.formatToCurrency(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    return NumberFormat.getCurrencyInstance(Locale.US).format(this).toString().dropLast(3)
}
