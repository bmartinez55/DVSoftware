package el.dv.presentation.view.effect

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.annotation.IdRes
import el.dv.domain.navigation.model.MapVisualType

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

    data class NavigateToGlobalActionEffect(@IdRes val actionId: Int) : ViewEffect()

    data class UpdateMapTypeEffect(val mapVisualType: MapVisualType) : ViewEffect()

    data class ShowMapConfigurationsScreenEffect(val mapVisualType: MapVisualType) : ViewEffect()
}
