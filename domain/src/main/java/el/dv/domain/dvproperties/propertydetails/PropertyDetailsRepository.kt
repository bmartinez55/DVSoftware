package el.dv.domain.dvproperties.propertydetails

import el.dv.domain.core.Result
import el.dv.domain.dvproperties.propertydetails.model.AddPropertyRequest
import el.dv.domain.dvproperties.propertydetails.model.PropertyDetails
import el.dv.domain.dvproperties.propertydetails.model.PropertyType

interface PropertyDetailsRepository {
    suspend fun getAllOwnedProperties(): Result<List<PropertyDetails>>

    suspend fun getOwnedPropertiesByType(propertyType: PropertyType): Result<List<PropertyDetails>>

    suspend fun addNewProperty(addPropertyRequest: AddPropertyRequest): Result<Boolean>

    suspend fun getPropertyById(propertyId: String): Result<PropertyDetails>
}
