package el.dv.dvpropertiesdata.network

import com.google.gson.Gson
import el.dv.data.extension.formatToCurrency
import el.dv.domain.core.CoroutineDispatchers
import el.dv.domain.core.Result
import el.dv.domain.dvproperties.propertydetails.PropertyDetailsRepository
import el.dv.domain.dvproperties.propertydetails.model.AddPropertyRequest
import el.dv.domain.dvproperties.propertydetails.model.ImagePaths
import el.dv.domain.dvproperties.propertydetails.model.PropertyDetails
import el.dv.domain.dvproperties.propertydetails.model.PropertyType
import el.dv.dvpropertiesdata.util.TypeConverter
import el.dv.dvpropertiesdata.util.fromJson
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

    override suspend fun addNewProperty(addPropertyRequest: AddPropertyRequest): Result<Boolean> = withContext(dispatchers.IO) {
        try {
            propertyDetailsDao.addNewProperty(addPropertyRequest.toDaoPropertyDetails())
            Result.Success(true)
        } catch (e: Exception) {
            Result.Failure(false)
        }
    }

    override suspend fun getPropertyById(propertyId: String): Result<PropertyDetails> = withContext(dispatchers.IO) {
        try {
            Result.Success(propertyDetailsDao.getPropertyById(propertyId).toPropertyDetails())
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
        propertyCost = this.propertyCost.formatToCurrency(),
        lotSize = this.lotSize,
        propertySize = this.propertySize,
        buildDate = this.buildDate,
        bedroomCount = this.bedroomCount,
        bathroomCount = this.bathroomCount,
        propertyType = this.propertyType.toPropertyType(),
        imagePaths = Gson().fromJson<ImagePaths>(this.imagePaths)
    )
}

private fun String.toPropertyType(): PropertyType {
    return when (this) {
        PropertyType.Commercial.name -> PropertyType.Commercial
        PropertyType.Multi.name -> PropertyType.Multi
        else -> PropertyType.SFH
    }
}

private fun AddPropertyRequest.toDaoPropertyDetails(): DaoPropertyDetails {
    return DaoPropertyDetails(
        address = this.address,
        city = this.city,
        state = this.state,
        zipCode = this.zipCode,
        propertyCost = this.propertyCost.toInt(),
        lotSize = this.lotSize,
        propertySize = this.propertySize,
        buildDate = this.buildDate,
        bedroomCount = this.bedroomCount,
        bathroomCount = this.bathroomCount,
        propertyType = this.propertyType.name,
        imagePaths = Gson().toJson(this.imagePaths)
    )
}
