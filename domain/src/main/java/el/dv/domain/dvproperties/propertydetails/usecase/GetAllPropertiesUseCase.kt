package el.dv.domain.dvproperties.propertydetails.usecase

import el.dv.domain.core.SuspendUseCase
import el.dv.domain.dvproperties.propertydetails.PropertyDetails
import el.dv.domain.dvproperties.propertydetails.PropertyDetailsRepository

class GetAllPropertiesUseCase(
    private val propertyDetailsRepository: PropertyDetailsRepository
) : SuspendUseCase<Unit, List<PropertyDetails>> {
    override suspend fun run(param: Unit): List<PropertyDetails> {
        return propertyDetailsRepository.getAllProperties()
    }
}
