package el.dv.fayucafinder.feature.login.auth.google.usecase

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.fayucafinder.feature.login.auth.google.GoogleAuthenticationProvider
import el.dv.fayucafinderdata.network.auth.model.AuthAccount
import el.dv.fayucafinderdata.network.auth.model.AuthData
import el.dv.fayucafinderdata.network.auth.model.IntentData

/**
 * Get google account after sign in
 */
class GetGoogleAccountUseCase(private val authenticationProvider: GoogleAuthenticationProvider) :
    SuspendUseCase<AuthData<IntentData>, Result<AuthAccount<GoogleSignInAccount>>> {

    override suspend fun run(authData: AuthData<IntentData>): Result<AuthAccount<GoogleSignInAccount>> {
        return authenticationProvider.getAuthAccount(authData = authData)
    }
}
