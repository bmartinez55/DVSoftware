package el.dv.fayucafinder.feature.login.auth.google

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import el.dv.data.network.auth.model.AuthData
import el.dv.data.network.auth.model.IntentData
import el.dv.fayucafinder.R

/**
 * Activity Contract to create an intent for Google authentication and
 * parse the returned result
 */
class GoogleAuthenticationActivityResultContract : ActivityResultContract<Int, AuthData<IntentData>?>() {

    override fun createIntent(context: Context, input: Int): Intent {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val signInClient = GoogleSignIn.getClient(context, signInOptions)
        return signInClient.signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): AuthData<IntentData>? {
        return intent?.let {
            AuthData(IntentData(intent, resultCode))
        }
    }
}
