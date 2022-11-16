package el.dv.data.network.auth.api

import el.dv.data.network.auth.model.AuthAccount
import el.dv.data.network.auth.model.AuthData
import el.dv.data.network.auth.model.AuthProviderRequest
import el.dv.domain.core.Result

/**
 * Base Interface to handle various authentication providers i.e. Firebase, Google etc.
 */
interface AuthProviderBase<AuthDataType, AccountType> {
    /**
     * Check if user is authenticated
     * @returns Boolean
     */
    fun checkIfUserIsAuthenticated(): Boolean

    /**
     * Get authentication account based on data received
     * @param AuthData<AuthDataType>
     * @return Result<AuthAccount<AccountType>>>
     */
    fun getAuthAccount(authData: AuthData<AuthDataType>): Result<AuthAccount<AccountType>>

    /**
     * Get current signed in account
     * @return Result<AuthAccount<AccountType>>
     */
    fun getSignedInAuthAccount(): Result<AuthAccount<AccountType>>

    /**
     * Sign out the user
     */
    fun signOutUser()
}

/**
 * Generic interface to handle various authentication providers with base auth provider
 */
interface AuthProvider<RequestType, AuthDataType, AccountType> : AuthProviderBase<AuthDataType, AccountType> {
    /**
     * Start authentication
     */
    fun startAuthentication(request: AuthProviderRequest<RequestType>)
}
