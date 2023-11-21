package el.dv.fayucafinder.feature.login.auth.firebase.usecase

import el.dv.domain.core.SuspendUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.FirebaseAuthenticationProviderForGoogleAccount
import el.dv.fayucafinderdata.di.SignOutUserScope
import el.dv.fayucafinderdata.di.fayucaFinderUserScope

/**
 * Signs out user from Firebase
 */
class FirebaseSignOutUserUseCase(
    private val authenticationProviderForGoogleAccount: FirebaseAuthenticationProviderForGoogleAccount
) : SuspendUseCase<Unit, Unit> {

    override suspend fun run(param: Unit) {
        authenticationProviderForGoogleAccount.signOutUser()
        fayucaFinderUserScope = SignOutUserScope()
    }
}
