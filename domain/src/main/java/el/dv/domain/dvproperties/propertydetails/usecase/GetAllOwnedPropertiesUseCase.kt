package el.dv.domain.dvproperties.propertydetails.usecase

import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.domain.dvproperties.propertydetails.PropertyDetailsRepository
import el.dv.domain.dvproperties.propertydetails.model.PropertyDetails

class GetAllOwnedPropertiesUseCase(
    private val propertyDetailsRepository: PropertyDetailsRepository
) : SuspendUseCase<Unit, Result<List<PropertyDetails>>> {
    override suspend fun run(param: Unit): Result<List<PropertyDetails>> {
        return propertyDetailsRepository.getAllOwnedProperties()
    }
}
