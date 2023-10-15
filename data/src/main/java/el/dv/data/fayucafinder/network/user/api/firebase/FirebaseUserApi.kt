package el.dv.data.fayucafinder.network.user.api.firebase

import com.google.firebase.database.FirebaseDatabase
import el.dv.data.fayucafinder.network.user.api.UserApi
import el.dv.data.fayucafinder.network.user.model.toFirebaseUser
import el.dv.domain.core.Result
import el.dv.domain.logging.AppLog
import el.dv.domain.fayucafinder.user.model.AddUserRequest
import el.dv.domain.fayucafinder.user.model.GetUserRequest
import el.dv.domain.fayucafinder.user.model.UpdateUserRequest
import el.dv.domain.fayucafinder.user.model.User
import kotlin.coroutines.suspendCoroutine

class FirebaseUserApi(private val userDatabase: FirebaseDatabase) : UserApi {

    override suspend fun addUser(addUserRequest: AddUserRequest): Result<Boolean> = suspendCoroutine { continuation ->
        try {
            val key = userDatabase.reference.child(addUserRequest.user.id).push().key
            key?.let {
                userDatabase.reference
                    .child(addUserRequest.user.email)
                    .child(it)
                    .setValue(addUserRequest.user.toFirebaseUser().toMap())
                    .addOnSuccessListener {
                        AppLog.d(TAG, "addUser success")
                        continuation.resumeWith(kotlin.Result.success(Result.Success(true)))
                    }
                    .addOnFailureListener { e ->
                        AppLog.e(TAG, "addUser failure error", e)
                        continuation.resumeWith(kotlin.Result.success(Result.Failure(e)))
                    }
            }
        } catch (e: Exception) {
            AppLog.e(TAG, "addUser catch exception error", e)
            continuation.resumeWith(kotlin.Result.success(Result.Failure(e)))
        }
    }

    override suspend fun updateUser(updateUserRequest: UpdateUserRequest): Result<Boolean> = suspendCoroutine { continuation ->
        try {
            userDatabase.reference
                .child(updateUserRequest.user.email)
                .updateChildren(updateUserRequest.user.toMapChildren())
                .addOnSuccessListener {
                    AppLog.d(TAG, "updateUser success")
                    continuation.resumeWith(kotlin.Result.success(Result.Success(true)))
                }
                .addOnFailureListener { e ->
                    AppLog.e(TAG, "updateUser failure error", e)
                    continuation.resumeWith(kotlin.Result.success(Result.Failure(e)))
                }
        } catch (e: Exception) {
            AppLog.e(TAG, "updateUser catch exception error", e)
            continuation.resumeWith(kotlin.Result.success(Result.Failure(e)))
        }
    }

    override suspend fun getUser(getUserRequest: GetUserRequest): Result<User> = suspendCoroutine { continuation ->
        try {
            userDatabase.reference
                .child(getUserRequest.email)
                .get()
                .addOnSuccessListener { data ->
                    when (data.hasChildren()) {
                        true -> {
                            AppLog.d(TAG, "getUser success")
                            continuation.resumeWith(kotlin.Result.success(Result.Success(data.getValue(User::class.java) ?: User())))
                        }
                        false -> {
                            AppLog.d(TAG, "getUser failure error: No User Found")
                            continuation.resumeWith(kotlin.Result.success(Result.Failure(Exception("User not found"))))
                        }
                    }
                }
        } catch (e: Exception) {
            AppLog.e(TAG, "getUser catch exception error", e)
            continuation.resumeWith(kotlin.Result.success(Result.Failure(e)))
        }
    }

    companion object {
        const val TAG = "FirebaseUserApi"
    }
}
