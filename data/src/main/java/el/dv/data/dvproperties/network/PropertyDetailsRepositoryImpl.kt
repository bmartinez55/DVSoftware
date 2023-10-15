package el.dv.data.dvproperties.network

import el.dv.domain.core.CoroutineDispatchers
import el.dv.domain.core.Result
import el.dv.domain.dvproperties.propertydetails.PropertyDetails
import el.dv.domain.dvproperties.propertydetails.PropertyDetailsRepository
import el.dv.domain.dvproperties.propertydetails.PropertyType
import kotlinx.coroutines.withContext

class PropertyDetailsRepositoryImpl(
    private val propertyDetailsDao: PropertyDetailsDao,
    private val dispatchers: CoroutineDispatchers,
) : PropertyDetailsRepository {
    override suspend fun getAllProperties(): List<PropertyDetails> = withContext(dispatchers.IO){
        propertyDetailsDao.getAllProperties().map { it.toPropertyDetails() }
    }
}

private fun DaoPropertyDetails.toPropertyDetails(): PropertyDetails {
    return PropertyDetails(
        id = this.id,
        address = this.address,
        city = this.city,
        state = this.state,
        zipCode = this.zipCode,
        propertyCost = this.propertyCost,
        lotSize = this.lotSize,
        propertySize = this.propertySize,
        buildDate = this.buildDate,
        bedroomCount = this.bedroomCount,
        bathroomCount = this.bathroomCount,
        propertyType = this.propertyType.toPropertyType()
    )
}

private fun String.toPropertyType(): PropertyType {
    return when (this) {
        PropertyType.Commercial.name -> PropertyType.Commercial
        PropertyType.Multi.name -> PropertyType.Multi
        else -> PropertyType.SFH
    }
}
