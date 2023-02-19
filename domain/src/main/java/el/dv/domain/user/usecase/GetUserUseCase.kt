package el.dv.domain.user.usecase

import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.domain.user.UserRepository
import el.dv.domain.user.model.GetUserRequest
import el.dv.domain.user.model.User

class GetUserUseCase(private val userRepository: UserRepository) : SuspendUseCase<GetUserRequest, Result<User>> {

    override suspend fun run(getUserRequest: GetUserRequest): Result<User> {
        return userRepository.getUser(getUserRequest = getUserRequest)
    }
}
