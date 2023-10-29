package el.dv.fayucafinderdata.network.user.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import el.dv.domain.fayucafinder.user.model.User

@IgnoreExtraProperties
data class FirebaseUser(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val fullName: String = "$firstName $lastName",
    val email: String = "",
    val phoneNumber: String = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "firstName" to firstName,
            "lastName" to lastName,
            "fullName" to fullName,
            "email" to email,
            "phoneNumber" to phoneNumber
        )
    }
}

fun User.toFirebaseUser(): FirebaseUser {
    return FirebaseUser(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        fullName = this.fullName,
        email = this.email,
        phoneNumber = this.phoneNumber
    )
}
