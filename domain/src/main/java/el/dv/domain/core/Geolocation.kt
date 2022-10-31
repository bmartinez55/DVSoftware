package el.dv.domain.core

data class Geolocation(
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val address: String = "",
    val accuracy: Float = 0.0f
) {
    fun isDefault(): Boolean {
        return lat == 0.0
    }
}
