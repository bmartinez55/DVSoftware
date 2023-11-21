package el.dv.fayucafinder.feature.login.auth.firebase

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import el.dv.domain.logging.AppLog
import el.dv.fayucafinderdata.network.auth.api.AuthProvider
import el.dv.fayucafinderdata.network.auth.model.AuthCallback
import el.dv.fayucafinderdata.network.auth.model.AuthData
import el.dv.fayucafinderdata.network.auth.model.AuthProviderRequest

class FirebaseAuthenticationProviderForGoogleAccount(private val authCallback: AuthCallback<AuthData<Task<AuthResult>>>) :
    FirebaseAuthenticationProvider(), AuthProvider<GoogleSignInAccount, Task<AuthResult>, FirebaseUser> {

    override fun startAuthentication(request: AuthProviderRequest<GoogleSignInAccount>) {
        val credential = GoogleAuthProvider.getCredential(request.data.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            AppLog.d(TAG, "startAuthentication successful ${task.isSuccessful} ${task.exception?.message ?: ""}")
            authCallback.handleAuthData(AuthData(task))
        }
    }

    companion object {
        const val TAG = "FirebaseAuthenticationProviderForGoogleAccount"
    }
}
