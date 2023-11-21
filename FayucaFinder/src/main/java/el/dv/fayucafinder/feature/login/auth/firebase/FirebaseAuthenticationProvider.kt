package el.dv.fayucafinder.feature.login.auth.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import el.dv.domain.core.Result
import el.dv.fayucafinderdata.network.auth.api.AuthProviderBase
import el.dv.fayucafinderdata.network.auth.model.AuthAccount
import el.dv.fayucafinderdata.network.auth.model.AuthData

/**
 * Firebase Authentication Provider implementation
 */
open class FirebaseAuthenticationProvider : AuthProviderBase<Task<AuthResult>, FirebaseUser> {

    val auth = Firebase.auth

    override fun checkIfUserIsAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    override fun getAuthAccount(authData: AuthData<Task<AuthResult>>): Result<AuthAccount<FirebaseUser>> {
        return when {
            authData.data.isSuccessful && authData.data.result.user != null -> Result.Success(AuthAccount(requireNotNull(authData.data.result.user)))
            else -> Result.Failure(Exception("Firebase authentication with Google Failed"))
        }
    }

    override fun getSignedInAuthAccount(): Result<AuthAccount<FirebaseUser>> {
        return when (auth.currentUser) {
            null -> Result.Failure(Exception("Firebase user is already signed out"))
            else -> Result.Success(AuthAccount(requireNotNull(auth.currentUser)))
        }
    }

    override fun signOutUser() {
        auth.signOut()
    }
}
