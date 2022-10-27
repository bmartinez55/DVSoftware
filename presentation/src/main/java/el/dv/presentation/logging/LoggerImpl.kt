package el.dv.presentation.logging

import android.util.Log
import el.dv.domain.logging.Logger

object LoggerImpl : Logger {

    override fun d(tag: String?, message: String) {
        Log.d(tag, message)
    }

    override fun e(tag: String?, message: String, error: Throwable?) {
        when (error) {
            null -> Log.e(tag, message)
            else -> Log.e(tag, message, error)
        }
    }

    override fun v(tag: String?, message: String) {
        Log.v(tag, message)
    }
}
