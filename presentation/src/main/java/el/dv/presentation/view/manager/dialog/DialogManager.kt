package el.dv.presentation.view.manager.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.DialogInterface.OnKeyListener
import android.view.LayoutInflater
import android.view.View
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
            this.title.text = title
            this.message.text = message
            dialog = Dialog(context, themeStyleResId)

            when (positiveClickListener) {
                null -> this.dialogPositiveButton.visibility = View.GONE
                else -> {
                    this.dialogPositiveButton.text = positiveButtonTitle
                    this.dialogPositiveButton.setOnClickListener(
                        positiveClickListener.toOnClickListener(requireNotNull(dialog))
                    )
                }
            }

            when (negativeClickListener) {
                null -> this.dialogNegativeButton.visibility = View.GONE
                else -> {
                    this.dialogNegativeButton.text = negativeButtonTitle
                    this.dialogNegativeButton.setOnClickListener(
                        negativeClickListener.toOnClickListener(requireNotNull(dialog))
                    )
                }
            }

            dialog?.setContentView(binding.root)
            dialog?.setCancelable(false)
            dialog?.setCanceledOnTouchOutside(false)
            dialog?.show()
        }
    }

    override fun hideDialog() {
        dialog?.dismiss()
    }
}

private fun DialogInterface.OnClickListener.toOnClickListener(dialog: Dialog): View.OnClickListener {
    return View.OnClickListener {
        dialog.dismiss()
    }
}
