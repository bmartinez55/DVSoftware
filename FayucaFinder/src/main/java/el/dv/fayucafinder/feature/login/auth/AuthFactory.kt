package el.dv.fayucafinder.feature.login.auth

/**
 * Factory to create auth contract, provider and use cases
 */
interface AuthFactory<
    AuthProviderParameters,
    AuthProvider,
    CheckIfUserIsAuthenticatedUseCase,
    SignInUserUseCase,
    GetAuthAccountUseCase,
    GetCurrentSignedInAccountUseCase,
    SignOutUserUseCase> {
    /**
     * Create instance of AuthProvider with AuthProviderParameters
     * @returns AuthProvider
     */
    fun getAuthProvider(authProviderParameters: AuthProviderParameters): AuthProvider

    /**
     * Create CheckIfUserIsAuthenticatedUseCase with AuthProvider
     * @returns CheckIfUserIsAuthenticatedUseCase
     */
    fun getCheckIfUserIsAuthenticatedUseCase(authProvider: AuthProvider): CheckIfUserIsAuthenticatedUseCase

    /**
     * Create SignInUserUseCase with AuthProvider
     * @returns SignInUserUseCase
     */
    fun getSignInUserUseCase(authProvider: AuthProvider): SignInUserUseCase

    /**
     * Create GetAuthAccountUseCase with AuthProvider
     * @returns GetAuthAccountUseCase
     */
    fun getGetAuthAccountUseCase(authProvider: AuthProvider): GetAuthAccountUseCase

    /**
     * Create GetCurrentSignedInAccountUseCase with AuthProvider
     * @returns GetCurrentSignedInAccountUseCase
     */
    fun getGetCurrentSignedInAccountUseCase(authProvider: AuthProvider): GetCurrentSignedInAccountUseCase

    /**
     * Create GoogleSignOutUserUseCase with AuthProvider
     * @returns GoogleSignOutUserUseCase
     */
    fun getGoogleSignOutUserUseCase(authProvider: AuthProvider): SignOutUserUseCase
}

interface UIAuthFactory<
    AuthResultContract,
    AuthProviderParameters,
    AuthProvider,
    CheckIfUserIsAuthenticatedUseCase,
    SignInUserUseCase,
    GetAuthAccountUseCase,
    GetCurrentSignedInAccountUseCase,
    SignOutUserUseCase> :
    AuthFactory<
        AuthProviderParameters,
        AuthProvider,
        CheckIfUserIsAuthenticatedUseCase,
        SignInUserUseCase,
        GetAuthAccountUseCase,
        GetCurrentSignedInAccountUseCase,
        SignOutUserUseCase> {
    fun getAuthResultContract(): AuthResultContract
}
