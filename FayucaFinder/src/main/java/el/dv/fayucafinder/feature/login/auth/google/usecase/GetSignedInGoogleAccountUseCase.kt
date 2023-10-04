package el.dv.fayucafinder.feature.login.auth.google.usecase

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import el.dv.data.fayucafinder.network.auth.model.AuthAccount
import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.fayucafinder.feature.login.auth.google.GoogleAuthenticationProvider

/**
 * Get current signed in google account
 */
class GetSignedInGoogleAccountUseCase(private val authenticationProvider: GoogleAuthenticationProvider) :
    SuspendUseCase<Unit, Result<AuthAccount<GoogleSignInAccount>>> {

    override suspend fun run(param: Unit): Result<AuthAccount<GoogleSignInAccount>> {
        return authenticationProvider.getSignedInAuthAccount()
    }
}
