package el.dv.fayucafinder.feature.login.auth.google.usecase

import android.content.Context
import el.dv.data.fayucafinder.network.auth.model.AuthProviderRequest
import el.dv.domain.core.SuspendUseCase
import el.dv.fayucafinder.feature.login.auth.google.GoogleAuthenticationProvider

/**
 * Sign in with Google
 */
class SignInWithGoogleUseCase(private val authenticationProvider: GoogleAuthenticationProvider) :
    SuspendUseCase<AuthProviderRequest<Context>, Unit> {

    override suspend fun run(request: AuthProviderRequest<Context>) {
        return authenticationProvider.startAuthentication(request = request)
    }
}
