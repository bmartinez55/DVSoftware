package el.dv.domain.fayucafinder.user.usecase

import el.dv.domain.core.SuspendUseCase
import el.dv.domain.fayucafinder.user.UserRepository
import el.dv.domain.fayucafinder.user.model.AddUserRequest

class AddUserUseCase(private val userRepository: UserRepository) : SuspendUseCase<AddUserRequest, Unit> {

    override suspend fun run(addUserRequest: AddUserRequest) {
        userRepository.addUser(addUserRequest = addUserRequest)
    }
}
