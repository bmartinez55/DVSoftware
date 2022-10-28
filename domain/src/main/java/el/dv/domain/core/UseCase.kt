package el.dv.domain.core

interface UseCase

interface MainUseCase<Param, Result> : UseCase {
    fun run(param: Param): Result
}

interface SuspendUseCase<Param, Result> : UseCase {
    suspend fun run(param: Param): Result
}
