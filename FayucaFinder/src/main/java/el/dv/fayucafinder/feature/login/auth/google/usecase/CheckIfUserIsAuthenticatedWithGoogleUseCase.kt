package el.dv.fayucafinder.feature.login.auth.google.usecase

import el.dv.domain.core.SuspendUseCase
import el.dv.fayucafinder.feature.login.auth.google.GoogleAuthenticationProvider

/**
 * Check if the user is authenticated based on Google
 */
class CheckIfUserIsAuthenticatedWithGoogleUseCase(private val authenticationProvider: GoogleAuthenticationProvider) :
    SuspendUseCase<Unit, Boolean> {

    override suspend fun run(param: Unit): Boolean {
        return authenticationProvider.checkIfUserIsAuthenticated()
    }
}
