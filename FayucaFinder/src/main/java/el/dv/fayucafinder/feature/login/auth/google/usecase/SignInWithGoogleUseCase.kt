package el.dv.fayucafinder.feature.login.auth.google.usecase

import android.content.Context
import el.dv.domain.core.SuspendUseCase
import el.dv.fayucafinder.feature.login.auth.google.GoogleAuthenticationProvider
import el.dv.fayucafinderdata.network.auth.model.AuthProviderRequest

/**
 * Sign in with Google
 */
class SignInWithGoogleUseCase(private val authenticationProvider: GoogleAuthenticationProvider) :
    SuspendUseCase<AuthProviderRequest<Context>, Unit> {

    override suspend fun run(request: AuthProviderRequest<Context>) {
        return authenticationProvider.startAuthentication(request = request)
    }
}
