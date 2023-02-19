package el.dv.domain.user.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val fullName: String = "$firstName $lastName",
    val email: String = "",
    val phoneNumber: String = ""
) : Parcelable {
    fun toMapChildren(): Map<String, Any?> {
        return mapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "fullName" to fullName,
            "email" to email,
            "phoneNumber" to phoneNumber
        )
    }
}

data class AddUserRequest(val user: User)

data class UpdateUserRequest(val user: User)

data class GetUserRequest(val email: String)
