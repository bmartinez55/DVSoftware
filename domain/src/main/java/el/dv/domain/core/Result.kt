package el.dv.domain.core

import java.lang.Exception

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val exception: Any) : Result<T>()
}

inline fun <reified T> Result<T>.ifSuccess(): T? {
    if (this is Result.Success) {
        return this.data
    }
    return null
}

inline fun <reified T> Result<T>.ifSuccess(crossinline action: (data: T) -> Unit): Result<T> {
    if (this is Result.Success) {
        action.invoke(this.data)
    }
    return this
}

inline fun <reified T> Result<T>.ifFailure(crossinline action: (data: Exception) -> Unit): Result<T> {
    if (this is Result.Failure) {
        action.invoke(this.exception as Exception)
    }
    return this
}

suspend inline fun <reified T> Result<T>.suspendIfSuccess(crossinline action: suspend (data: T) -> Unit): Result<T> {
    if (this is Result.Success) {
        action.invoke(this.data)
    }
    return this
}
