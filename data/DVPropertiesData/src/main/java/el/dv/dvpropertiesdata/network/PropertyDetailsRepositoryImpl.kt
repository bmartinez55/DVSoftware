package el.dv.dvpropertiesdata.network

import el.dv.domain.core.CoroutineDispatchers
import el.dv.domain.core.Result
import el.dv.domain.dvproperties.propertydetails.PropertyDetailsRepository
import el.dv.domain.dvproperties.propertydetails.model.PropertyDetails
import el.dv.domain.dvproperties.propertydetails.model.PropertyType
import kotlinx.coroutines.withContext

class PropertyDetailsRepositoryImpl(
    private val propertyDetailsDao: PropertyDetailsDao,
    private val dispatchers: CoroutineDispatchers
) : PropertyDetailsRepository {
    override suspend fun getAllOwnedProperties(): Result<List<PropertyDetails>> = withContext(dispatchers.IO) {
        try {
            Result.Success(propertyDetailsDao.getAllProperties().map { it.toPropertyDetails() })
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getOwnedPropertiesByType(propertyType: PropertyType): Result<List<PropertyDetails>> = withContext(dispatchers.IO) {
        try {
            Result.Success(propertyDetailsDao.getAllPropertiesByType(propertyType.name).map { it.toPropertyDetails() })
        } catch (e: Exception) {
            Result.Failure(e)
        }
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
