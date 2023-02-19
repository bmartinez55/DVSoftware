package el.dv.domain.user

import el.dv.domain.core.Result
import el.dv.domain.user.model.AddUserRequest
import el.dv.domain.user.model.GetUserRequest
import el.dv.domain.user.model.UpdateUserRequest
import el.dv.domain.user.model.User

/**
 * Repository to get user data
 */
interface UserRepository {

    /**
     * Add User
     * @param addUserRequest
     * @return Result<Boolean>
     */
    suspend fun addUser(addUserRequest: AddUserRequest): Result<Boolean>

    /**
     * Update User
     * @param updateUserRequest
     * @return Result<Boolean>
     */
    suspend fun updateUser(updateUserRequest: UpdateUserRequest): Result<Boolean>

    /**
     * Get User
     * @param getUserRequest
     * @return Result<User>
     */
    suspend fun getUser(getUserRequest: GetUserRequest): Result<User>
}
