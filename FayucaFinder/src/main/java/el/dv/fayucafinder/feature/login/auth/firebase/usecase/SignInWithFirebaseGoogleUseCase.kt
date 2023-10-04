package el.dv.fayucafinder.feature.login.auth.firebase.usecase

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import el.dv.data.fayucafinder.network.auth.model.AuthProviderRequest
import el.dv.domain.core.SuspendUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.FirebaseAuthenticationProviderForGoogleAccount

/**
 * Sign with Google using Firebase
 */
class SignInWithFirebaseGoogleUseCase(private val authenticationProviderForGoogleAccount: FirebaseAuthenticationProviderForGoogleAccount) :
    SuspendUseCase<AuthProviderRequest<GoogleSignInAccount>, Unit> {

    override suspend fun run(request: AuthProviderRequest<GoogleSignInAccount>) {
        return authenticationProviderForGoogleAccount.startAuthentication(request = request)
    }
}
