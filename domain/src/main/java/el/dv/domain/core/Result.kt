package el.dv.domain.core

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val data: T) : Result<T>()
}
