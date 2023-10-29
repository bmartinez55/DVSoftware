package el.dv.fayucafinder.feature.login.auth.google

import el.dv.fayucafinderdata.network.auth.model.AuthCallback
import el.dv.fayucafinderdata.network.auth.model.AuthData
import el.dv.fayucafinderdata.network.auth.model.IntentData
import el.dv.fayucafinder.feature.login.auth.UIAuthFactory
import el.dv.fayucafinder.feature.login.auth.google.usecase.CheckIfUserIsAuthenticatedWithGoogleUseCase
import el.dv.fayucafinder.feature.login.auth.google.usecase.GetGoogleAccountUseCase
import el.dv.fayucafinder.feature.login.auth.google.usecase.GetSignedInGoogleAccountUseCase
import el.dv.fayucafinder.feature.login.auth.google.usecase.GoogleSignOutUserUseCase
import el.dv.fayucafinder.feature.login.auth.google.usecase.SignInWithGoogleUseCase
import el.dv.fayucafinder.feature.login.auth.model.UIAuthProviderParams

/**
 * Google Authentication Factory implementation
 */
class GoogleAuthenticationFactory :
    UIAuthFactory<
        GoogleAuthenticationActivityResultContract,
        UIAuthProviderParams<GoogleAuthenticationActivityResultContract, AuthCallback<AuthData<IntentData>>>,
        GoogleAuthenticationProvider,
        CheckIfUserIsAuthenticatedWithGoogleUseCase,
        SignInWithGoogleUseCase,
        GetGoogleAccountUseCase,
        GetSignedInGoogleAccountUseCase,
        GoogleSignOutUserUseCase> {
    override fun getAuthResultContract(): GoogleAuthenticationActivityResultContract {
        return GoogleAuthenticationActivityResultContract()
    }

    override fun getAuthProvider(
        authProviderParameters: UIAuthProviderParams<GoogleAuthenticationActivityResultContract, AuthCallback<AuthData<IntentData>>>
    ): GoogleAuthenticationProvider {
        return GoogleAuthenticationProvider(
            activityResultContract = authProviderParameters.authResultContract,
            authCallback = authProviderParameters.authCallback,
            fragment = authProviderParameters.fragment,
            context = authProviderParameters.context
        )
    }

    override fun getCheckIfUserIsAuthenticatedUseCase(authProvider: GoogleAuthenticationProvider): CheckIfUserIsAuthenticatedWithGoogleUseCase {
        return CheckIfUserIsAuthenticatedWithGoogleUseCase(authenticationProvider = authProvider)
    }

    override fun getSignInUserUseCase(authProvider: GoogleAuthenticationProvider): SignInWithGoogleUseCase {
        return SignInWithGoogleUseCase(authenticationProvider = authProvider)
    }

    override fun getGetAuthAccountUseCase(authProvider: GoogleAuthenticationProvider): GetGoogleAccountUseCase {
        return GetGoogleAccountUseCase(authenticationProvider = authProvider)
    }

    override fun getGetCurrentSignedInAccountUseCase(authProvider: GoogleAuthenticationProvider): GetSignedInGoogleAccountUseCase {
        return GetSignedInGoogleAccountUseCase(authenticationProvider = authProvider)
    }

    override fun getGoogleSignOutUserUseCase(authProvider: GoogleAuthenticationProvider): GoogleSignOutUserUseCase {
        return GoogleSignOutUserUseCase(authenticationProvider = authProvider)
    }
}
