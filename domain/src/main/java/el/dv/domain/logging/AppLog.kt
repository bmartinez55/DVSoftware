package el.dv.domain.logging

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface Logger {
    fun d(tag: String?, message: String)
    fun e(tag: String?, message: String, error: Throwable?)
    fun v(tag: String?, message: String)
}

object AppLog : KoinComponent {
    private val logger: Logger by inject()

    fun d(message: String) {
        logger.d(null, message)
    }

    fun d(tag: String?, message: String) {
        logger.d(tag, message)
    }

    fun e(message: String) {
        logger.e(null, message, null)
    }

    fun e(tag: String?, message: String) {
        logger.e(tag, message, null)
    }

    fun e(tag: String?, message: String, error: Throwable?) {
        logger.e(tag, message, error)
    }

    fun v(tag: String?, message: String) {
        logger.v(tag, message)
    }
}