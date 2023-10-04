package el.dv.domain.dvproperties.propertydetails

import el.dv.domain.core.Result
import el.dv.domain.dvproperties.propertydetails.PropertyDetails

interface PropertyDetailsRepository {
    suspend fun getAllProperties(): Result<List<PropertyDetails>>
}
