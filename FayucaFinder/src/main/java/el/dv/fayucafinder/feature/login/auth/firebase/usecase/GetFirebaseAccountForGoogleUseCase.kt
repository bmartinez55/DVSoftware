package el.dv.fayucafinder.feature.login.auth.firebase.usecase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.FirebaseAuthenticationProviderForGoogleAccount
import el.dv.fayucafinderdata.network.auth.model.AuthAccount
import el.dv.fayucafinderdata.network.auth.model.AuthData

/**
 * Get Google account from Firebase after signing in
 */
class GetFirebaseAccountForGoogleUseCase(private val authenticationProviderForGoogleAccount: FirebaseAuthenticationProviderForGoogleAccount) :
    SuspendUseCase<AuthData<Task<AuthResult>>, Result<AuthAccount<FirebaseUser>>> {

    override suspend fun run(authData: AuthData<Task<AuthResult>>): Result<AuthAccount<FirebaseUser>> {
        return authenticationProviderForGoogleAccount.getAuthAccount(authData = authData)
    }
}
