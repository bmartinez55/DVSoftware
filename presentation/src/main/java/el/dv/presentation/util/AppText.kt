package el.dv.presentation.util

import android.content.Context
import androidx.annotation.StringRes

interface AppDictionary {
    fun toString(appText: AppText): String
}

class AppDictionaryImpl(private val context: Context) : AppDictionary {
    override fun toString(appText: AppText): String {
        return when (appText) {
            is AppText.DynamicText -> appText.value
            is AppText.TranslatableText -> context.getString(appText.resId, appText.args)
        }
    }
}

sealed class AppText {
    data class DynamicText(val value: String) : AppText()
    class TranslatableText(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : AppText()
}
