package el.dv.domain.dvproperties.propertydetails.usecase

import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.domain.dvproperties.propertydetails.PropertyDetailsRepository
import el.dv.domain.dvproperties.propertydetails.model.PropertyDetails
import el.dv.domain.dvproperties.propertydetails.model.PropertyType

class GetOwnedPropertiesByTypeUseCase(
    private val propertyDetailsRepository: PropertyDetailsRepository
) : SuspendUseCase<PropertyType, Result<List<PropertyDetails>>> {
    override suspend fun run(param: PropertyType): Result<List<PropertyDetails>> {
        return propertyDetailsRepository.getOwnedPropertiesByType(param)
    }
}
