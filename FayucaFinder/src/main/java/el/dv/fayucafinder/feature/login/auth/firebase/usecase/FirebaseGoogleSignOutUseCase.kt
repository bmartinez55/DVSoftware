package el.dv.fayucafinder.feature.login.auth.firebase.usecase

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import el.dv.data.di.scope.SignOutUserScope
import el.dv.data.di.scope.fayucaFinderUserScope
import el.dv.domain.core.SuspendUseCase
import el.dv.domain.logging.AppLog

class FirebaseGoogleSignOutUseCase(private val context: Context) : SuspendUseCase<Unit, Unit> {

    override suspend fun run(param: Unit) {
        try {
            Firebase.auth.signOut()
            val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            val signInClient = GoogleSignIn.getClient(context, signInOptions)
            signInClient.signOut()
            fayucaFinderUserScope = SignOutUserScope()
        } catch (e: Exception) {
            AppLog.e(TAG, "sign out error", e)
        }
    }

    companion object {
        const val TAG = "FirebaseGoogleSignOutUseCase"
    }
}
