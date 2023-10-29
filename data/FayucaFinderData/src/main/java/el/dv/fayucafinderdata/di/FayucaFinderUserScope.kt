package el.dv.fayucafinderdata.di

import android.os.Parcelable
import el.dv.domain.logging.AppLog
import kotlinx.parcelize.Parcelize

var fayucaFinderUserScope: FayucaFinderUserScope = SignOutUserScope()
    @Synchronized
    set(value) {
        AppLog.d(FAYUCA_FINDER_USER_SCOPE_TAG, "scope set")
        field.close()
        field = value
        field.open()
    }

/**
 * Interface to handle user scopes i.e. signedIn or signedOut
 */
interface FayucaFinderUserScope {
    fun open()
    fun close()
    fun getUserData(): UserScopeData
}

sealed class UserScopeData {
    @Parcelize
    data class UserData(
        val userTokenId: String,
        val userEmail: String = "",
        val tokenIdExpirationEpochTime: Long
    ) : UserScopeData(), Parcelable

    @Parcelize
    data class TruckOwnerUserData(
        val userTokenId: String,
        val userEmail: String = "",
        val ownerId: String = "",
        val tokenIdExpirationEpochTime: Long
    ) : UserScopeData(), Parcelable

    object NoData : UserScopeData()
}

@Parcelize
class SignInUserScope(val userScopeData: UserScopeData.UserData) : FayucaFinderUserScope, Parcelable {
    override fun open() {
        // TODO("Not yet implemented")
    }

    override fun close() {
        // TODO("Not yet implemented")
    }

    override fun getUserData(): UserScopeData {
        return userScopeData
    }
}

@Parcelize
class SignOutUserScope : FayucaFinderUserScope, Parcelable {
    override fun open() {
        // TODO("Not yet implemented")
    }

    override fun close() {
        // TODO("Not yet implemented")
    }

    override fun getUserData(): UserScopeData {
        return UserScopeData.NoData
    }
}

const val FAYUCA_FINDER_USER_SCOPE_TAG = "FayucaFinderUserScope"
