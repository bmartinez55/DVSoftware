package el.dv.domain.dvproperties.propertydetails.usecase

import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.domain.dvproperties.propertydetails.PropertyDetailsRepository
import el.dv.domain.dvproperties.propertydetails.model.AddPropertyRequest

class AddNewPropertyUseCase(
    private val propertyDetailsRepository: PropertyDetailsRepository
) : SuspendUseCase<AddPropertyRequest, Result<Boolean>> {
    override suspend fun run(addPropertyRequest: AddPropertyRequest): Result<Boolean> {
        return propertyDetailsRepository.addNewProperty(addPropertyRequest)
    }
}
