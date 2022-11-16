package el.dv.fayucafinder.feature.login.auth.firebase.usecase

import el.dv.data.di.scope.SignOutUserScope
import el.dv.data.di.scope.fayucaFinderUserScope
import el.dv.domain.core.SuspendUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.FirebaseAuthenticationProviderForGoogleAccount

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
