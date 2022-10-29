package el.dv.presentation.view.dialogmanager

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface.OnClickListener
import android.content.DialogInterface.OnKeyListener
import android.view.LayoutInflater
import androidx.annotation.StyleRes
import el.dv.presentation.databinding.DialogVerticalLayoutBinding

interface DialogManager {
    fun showDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonTitle: String = "",
        positiveClickListener: OnClickListener? = null,
        negativeButtonTitle: String = "",
        negativeClickListener: OnClickListener? = null,
        onKeyListener: OnKeyListener? = null
    )

    fun hideDialog()
}

class DialogManagerVertical(@StyleRes val themeStyleResId: Int) : DialogManager {

    private var dialog: Dialog? = null

    override fun showDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonTitle: String,
        positiveClickListener: OnClickListener?,
        negativeButtonTitle: String,
        negativeClickListener: OnClickListener?,
        onKeyListener: OnKeyListener?
    ) {
        val binding = DialogVerticalLayoutBinding.inflate(LayoutInflater.from(context), null, false)

        with(binding) {
        }
    }

    override fun hideDialog() {
        dialog?.dismiss()
    }
}
