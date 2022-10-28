package el.dv.domain.location

data class Geolocation(
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val address: String = "",
    val accuracy: Double = 0.0
)
