package el.dv.data.dvproperties.network

import androidx.room.Dao
import androidx.room.Query
import el.dv.domain.core.Result
import el.dv.domain.dvproperties.propertydetails.PropertyDetails

@Dao
interface PropertyDetailsDao {
    @Query("SELECT * FROM propertyDetailsTable ORDER BY id ASC")
    suspend fun getAllProperties(): Result<List<PropertyDetails>>
}
