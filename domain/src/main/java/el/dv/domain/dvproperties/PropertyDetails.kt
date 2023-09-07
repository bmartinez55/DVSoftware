package el.dv.domain.dvproperties

data class PropertyDetails(
    val id: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val zipCode: String = "",
    val propertyValue: String = "",
    val lotSize: String = "",
    val propertySize: String = "",
    val buildDate: String = "",
    val bedroomCount: String = "",
    val bathroomCount: String = "",
    val propertyType: PropertyType = PropertyType.SFH
)

enum class PropertyType {
    SFH,
    Multi,
    Commercial
}
