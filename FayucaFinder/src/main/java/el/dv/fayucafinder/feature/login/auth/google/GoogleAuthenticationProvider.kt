package el.dv.fayucafinder.feature.login.auth.google

import android.app.Activity.RESULT_OK
import android.content.Context
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import el.dv.fayucafinderdata.network.auth.api.AuthProvider
import el.dv.fayucafinderdata.network.auth.model.AuthAccount
import el.dv.fayucafinderdata.network.auth.model.AuthCallback
import el.dv.fayucafinderdata.network.auth.model.AuthData
import el.dv.fayucafinderdata.network.auth.model.AuthProviderRequest
import el.dv.fayucafinderdata.network.auth.model.IntentData
import el.dv.domain.core.Result
import el.dv.domain.logging.AppLog

/**
 * Google Authentication provider implementation
 */
class GoogleAuthenticationProvider(
    activityResultContract: GoogleAuthenticationActivityResultContract,
    private val authCallback: AuthCallback<AuthData<IntentData>>,
    fragment: Fragment,
    private val context: Context
) : AuthProvider<Context, IntentData, GoogleSignInAccount> {

    /**
     * Register Activity Contract
     */
    private val googleAuthenticationResultLauncher = fragment.registerForActivityResult(activityResultContract) { authData ->
        if (authData != null) {
            authCallback.handleAuthData(authData)
        }
    }

    override fun startAuthentication(request: AuthProviderRequest<Context>) {
        AppLog.d(TAG, "startAuthentication")
        googleAuthenticationResultLauncher.launch(RC_SIGN_IN)
    }

    override fun checkIfUserIsAuthenticated(): Boolean {
        AppLog.d(TAG, "checkIfUserIsAuthenticated")
        return GoogleSignIn.getLastSignedInAccount(context) != null
    }

    override fun getAuthAccount(authData: AuthData<IntentData>): Result<AuthAccount<GoogleSignInAccount>> {
        AppLog.d(TAG, "getAuthAccount")
        return try {
            when (authData.data.resultCode) {
                RESULT_OK -> {
                    val signInTask = GoogleSignIn.getSignedInAccountFromIntent(authData.data.intentData)
                    val account = signInTask.getResult(ApiException::class.java)

                    account?.let {
                        Result.Success(AuthAccount(account))
                    } ?: Result.Failure(Exception("Google account was not found"))
                }
                else -> Result.Failure(Exception("Google Sign in result was not OK"))
            }
        } catch (e: Exception) {
            AppLog.e(TAG, "getAuthAccount catch error", e)
            Result.Failure(e)
        }
    }

    override fun getSignedInAuthAccount(): Result<AuthAccount<GoogleSignInAccount>> {
        AppLog.d(TAG, "getSignedInAuthAccount")
        return when (val account = GoogleSignIn.getLastSignedInAccount(context)) {
            null -> Result.Failure(Exception("Google account was not found"))
            else -> Result.Success(AuthAccount(account))
        }
    }

    override fun signOutUser() {
        AppLog.d(TAG, "signOutUser")
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val signInClient = GoogleSignIn.getClient(context, signInOptions)
        signInClient.signOut()
    }

    companion object {
        const val TAG = "GoogleAuthenticationProvider"
        const val RC_SIGN_IN = 1515
    }
}
