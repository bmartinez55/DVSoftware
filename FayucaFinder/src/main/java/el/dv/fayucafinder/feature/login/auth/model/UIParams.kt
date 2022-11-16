package el.dv.fayucafinder.feature.login.auth.model

import android.content.Context
import androidx.fragment.app.Fragment

data class AuthProviderParams<Type>(val data: Type)

data class UIAuthProviderParams<AuthResultContract, AuthCallback>(
    val authResultContract: AuthResultContract,
    val authCallback: AuthCallback,
    val fragment: Fragment,
    val context: Context
)
