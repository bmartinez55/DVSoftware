package el.dv.fayucafinder.feature.login.auth.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import el.dv.data.fayucafinder.network.auth.model.AuthCallback
import el.dv.data.fayucafinder.network.auth.model.AuthData
import el.dv.fayucafinder.feature.login.auth.AuthFactory
import el.dv.fayucafinder.feature.login.auth.firebase.usecase.CheckIfUserIsAuthenticatedWithFirebaseGoogleUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.usecase.FirebaseSignOutUserUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.usecase.GetFirebaseAccountForGoogleUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.usecase.GetSignedInFirebaseGoogleAccountUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.usecase.SignInWithFirebaseGoogleUseCase

class FirebaseAuthenticationFactoryForGoogle :
    AuthFactory<
            AuthCallback<AuthData<Task<AuthResult>>>,
        FirebaseAuthenticationProviderForGoogleAccount,
        CheckIfUserIsAuthenticatedWithFirebaseGoogleUseCase,
        SignInWithFirebaseGoogleUseCase,
        GetFirebaseAccountForGoogleUseCase,
        GetSignedInFirebaseGoogleAccountUseCase,
        FirebaseSignOutUserUseCase> {

    override fun getAuthProvider(authCallback: AuthCallback<AuthData<Task<AuthResult>>>): FirebaseAuthenticationProviderForGoogleAccount {
        return FirebaseAuthenticationProviderForGoogleAccount(authCallback = authCallback)
    }

    override fun getCheckIfUserIsAuthenticatedUseCase(authProvider: FirebaseAuthenticationProviderForGoogleAccount): CheckIfUserIsAuthenticatedWithFirebaseGoogleUseCase {
        return CheckIfUserIsAuthenticatedWithFirebaseGoogleUseCase(authenticationProviderForGoogleAccount = authProvider)
    }

    override fun getSignInUserUseCase(authProvider: FirebaseAuthenticationProviderForGoogleAccount): SignInWithFirebaseGoogleUseCase {
        return SignInWithFirebaseGoogleUseCase(authenticationProviderForGoogleAccount = authProvider)
    }

    override fun getGetAuthAccountUseCase(authProvider: FirebaseAuthenticationProviderForGoogleAccount): GetFirebaseAccountForGoogleUseCase {
        return GetFirebaseAccountForGoogleUseCase(authenticationProviderForGoogleAccount = authProvider)
    }

    override fun getGetCurrentSignedInAccountUseCase(authProvider: FirebaseAuthenticationProviderForGoogleAccount): GetSignedInFirebaseGoogleAccountUseCase {
        return GetSignedInFirebaseGoogleAccountUseCase(authenticationProviderForGoogleAccount = authProvider)
    }

    override fun getGoogleSignOutUserUseCase(authProvider: FirebaseAuthenticationProviderForGoogleAccount): FirebaseSignOutUserUseCase {
        return FirebaseSignOutUserUseCase(authenticationProviderForGoogleAccount = authProvider)
    }
}
