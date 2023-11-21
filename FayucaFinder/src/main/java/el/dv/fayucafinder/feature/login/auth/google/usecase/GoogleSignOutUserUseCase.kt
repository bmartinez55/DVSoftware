package el.dv.fayucafinder.feature.login.auth.google.usecase

import el.dv.domain.core.SuspendUseCase
import el.dv.fayucafinder.feature.login.auth.google.GoogleAuthenticationProvider
import el.dv.fayucafinderdata.di.SignOutUserScope
import el.dv.fayucafinderdata.di.fayucaFinderUserScope

/**
 * Signs out user from Google
 */
class GoogleSignOutUserUseCase(private val authenticationProvider: GoogleAuthenticationProvider) : SuspendUseCase<Unit, Unit> {

    override suspend fun run(param: Unit) {
        authenticationProvider.signOutUser()
        fayucaFinderUserScope = SignOutUserScope()
    }
}
