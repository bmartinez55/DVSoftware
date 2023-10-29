package el.dv.fayucafinderdata.network.auth.model

import android.content.Intent

data class AuthProviderRequest<Type>(val data: Type)

data class AuthData<Type>(val data: Type)

data class AuthAccount<AccountType>(val userAccount: AccountType)

fun interface AuthCallback<AuthDataType> {
    fun handleAuthData(authDataType: AuthDataType)
}

data class IntentData(val intentData: Intent, val resultCode: Int)

enum class AuthType {
    EmailLink,
    Google
}
