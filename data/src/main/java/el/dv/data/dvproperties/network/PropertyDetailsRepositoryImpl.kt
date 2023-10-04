package el.dv.data.dvproperties.network

import el.dv.domain.core.CoroutineDispatchers
import el.dv.domain.core.Result
import el.dv.domain.dvproperties.propertydetails.PropertyDetails
import el.dv.domain.dvproperties.propertydetails.PropertyDetailsRepository
import kotlinx.coroutines.withContext

class PropertyDetailsRepositoryImpl(
    private val propertyDetailsDao: PropertyDetailsDao,
    private val dispatchers: CoroutineDispatchers,
) : PropertyDetailsRepository {
    override suspend fun getAllProperties(): Result<List<PropertyDetails>> = withContext(dispatchers.IO){
        propertyDetailsDao.getAllProperties()
    }
}
