package el.dv.data.fayucafinder.network.user.repository

import el.dv.data.fayucafinder.network.user.api.UserApi
import el.dv.domain.core.CoroutineDispatchers
import el.dv.domain.core.Result
import el.dv.domain.fayucafinder.user.UserRepository
import el.dv.domain.fayucafinder.user.model.AddUserRequest
import el.dv.domain.fayucafinder.user.model.GetUserRequest
import el.dv.domain.fayucafinder.user.model.UpdateUserRequest
import el.dv.domain.fayucafinder.user.model.User
import kotlinx.coroutines.withContext

/**
 * Repository implementation to get user data
 */
class UserRepositoryImpl(
    private val userApi: UserApi,
    private val dispatchers: CoroutineDispatchers
) : UserRepository {

    override suspend fun addUser(addUserRequest: AddUserRequest): Result<Boolean> = withContext(dispatchers.IO) {
        userApi.addUser(addUserRequest = addUserRequest)
    }

    override suspend fun updateUser(updateUserRequest: UpdateUserRequest): Result<Boolean> = withContext(dispatchers.IO) {
        userApi.updateUser(updateUserRequest = updateUserRequest)
    }

    override suspend fun getUser(getUserRequest: GetUserRequest): Result<User> = withContext(dispatchers.IO) {
        userApi.getUser(getUserRequest = getUserRequest)
    }
}
