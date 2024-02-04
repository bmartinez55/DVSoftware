package el.dv.domain.dvproperties.propertydetails.usecase

import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.domain.dvproperties.propertydetails.PropertyDetailsRepository
import el.dv.domain.dvproperties.propertydetails.model.PropertyDetails

class GetPropertyByIdUseCase(
    private val propertyDetailsRepository: PropertyDetailsRepository
) : SuspendUseCase<String, Result<PropertyDetails>> {
    override suspend fun run(id: String): Result<PropertyDetails> {
        return propertyDetailsRepository.getPropertyById(id)
    }
}
