package el.dv.fayucafinder.feature.login.auth.firebase.usecase

import el.dv.domain.core.SuspendUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.FirebaseAuthenticationProviderForGoogleAccount

/**
 * Check if user is authenticated using Firebase
 */
class CheckIfUserIsAuthenticatedWithFirebaseGoogleUseCase(
    private val authenticationProviderForGoogleAccount: FirebaseAuthenticationProviderForGoogleAccount
) : SuspendUseCase<Unit, Boolean> {

    override suspend fun run(param: Unit): Boolean {
        return authenticationProviderForGoogleAccount.checkIfUserIsAuthenticated()
    }
}
