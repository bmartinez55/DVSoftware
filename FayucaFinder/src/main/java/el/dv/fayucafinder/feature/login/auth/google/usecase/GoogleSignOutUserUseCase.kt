package el.dv.fayucafinder.feature.login.auth.google.usecase

import el.dv.data.fayucafinder.di.SignOutUserScope
import el.dv.data.fayucafinder.di.fayucaFinderUserScope
import el.dv.domain.core.SuspendUseCase
import el.dv.fayucafinder.feature.login.auth.google.GoogleAuthenticationProvider

/**
 * Signs out user from Google
 */
class GoogleSignOutUserUseCase(private val authenticationProvider: GoogleAuthenticationProvider) : SuspendUseCase<Unit, Unit> {

    override suspend fun run(param: Unit) {
        authenticationProvider.signOutUser()
        fayucaFinderUserScope = SignOutUserScope()
    }
}
