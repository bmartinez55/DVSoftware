package el.dv.fayucafinder.feature.login.auth.firebase.usecase

import com.google.firebase.auth.FirebaseUser
import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.FirebaseAuthenticationProviderForGoogleAccount
import el.dv.fayucafinderdata.network.auth.model.AuthAccount

class GetSignedInFirebaseGoogleAccountUseCase(
    private val authenticationProviderForGoogleAccount: FirebaseAuthenticationProviderForGoogleAccount
) : SuspendUseCase<Unit, Result<AuthAccount<FirebaseUser>>> {

    override suspend fun run(param: Unit): Result<AuthAccount<FirebaseUser>> {
        return authenticationProviderForGoogleAccount.getSignedInAuthAccount()
    }
}
