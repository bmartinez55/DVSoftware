package el.dv.domain.dvproperties.propertydetails

interface PropertyDetailsRepository {
    suspend fun getAllProperties(): List<PropertyDetails>
}
