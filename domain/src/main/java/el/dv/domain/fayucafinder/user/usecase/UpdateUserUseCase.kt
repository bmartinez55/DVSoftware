package el.dv.domain.fayucafinder.user.usecase

import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.domain.fayucafinder.user.UserRepository
import el.dv.domain.fayucafinder.user.model.UpdateUserRequest

class UpdateUserUseCase(private val userRepository: UserRepository) : SuspendUseCase<UpdateUserRequest, Result<Boolean>> {

    override suspend fun run(updateUserRequest: UpdateUserRequest): Result<Boolean> {
        return userRepository.updateUser(updateUserRequest = updateUserRequest)
    }
}
