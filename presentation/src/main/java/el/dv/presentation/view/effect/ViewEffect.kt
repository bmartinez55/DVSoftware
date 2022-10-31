package el.dv.presentation.view.effect

import android.content.Context
import android.content.DialogInterface
import android.content.Intent

sealed class ViewEffect {
    object Default : ViewEffect()

    data class ShowDialogEffect(
        val context: Context,
        val title: String,
        val message: String,
        val positiveButtonTitle: String = "",
        val positiveClickListener: DialogInterface.OnClickListener? = null,
        val negativeButtonTitle: String = "",
        val negativeClickListener: DialogInterface.OnClickListener? = null,
        val onKeyListener: DialogInterface.OnKeyListener? = null
    ) : ViewEffect()

    object DismissDialogEffect : ViewEffect()

    data class StartActivityEffect(val intent: Intent) : ViewEffect()
}
