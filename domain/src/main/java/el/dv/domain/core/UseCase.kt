package el.dv.domain.core

interface UseCase

interface DefaultUseCase<Param, Result> : UseCase {
    fun run(param: Param): Result
}

interface SuspendUseCase<Param, Result> : UseCase {
    suspend fun run(param: Param): Result
}

interface ViewReducer<Param, Result> : UseCase {
    fun run(param: Param): Result
}
