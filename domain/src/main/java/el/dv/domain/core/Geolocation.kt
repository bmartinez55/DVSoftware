package el.dv.domain.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Geolocation(
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val address: String = "",
    val accuracy: Float = 0.0f
) : Parcelable {
    fun isDefault(): Boolean {
        return lat == 0.0
    }
}
