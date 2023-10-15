package el.dv.data.dvproperties.network

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import el.dv.domain.core.Result
import el.dv.domain.dvproperties.propertydetails.PropertyDetails
import el.dv.domain.dvproperties.propertydetails.PropertyType
import kotlinx.parcelize.Parcelize

@Dao
interface PropertyDetailsDao {
    @Query("SELECT * FROM propertyDetailsTable ORDER BY id ASC")
    suspend fun getAllProperties(): List<DaoPropertyDetails>
}

@Parcelize
@Entity(tableName = "propertyDetailsTable")
data class DaoPropertyDetails(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo("address") val address: String = "",
    @ColumnInfo("city") val city: String = "",
    @ColumnInfo("state") val state: String = "",
    @ColumnInfo("zip_code") val zipCode: String = "",
    @ColumnInfo("property_cost") val propertyCost: String = "",
    @ColumnInfo("lot_size") val lotSize: String = "",
    @ColumnInfo("property_size") val propertySize: String = "",
    @ColumnInfo("build_date") val buildDate: String = "",
    @ColumnInfo("bedroom_count") val bedroomCount: String = "",
    @ColumnInfo("bathroom_count") val bathroomCount: String = "",
    @ColumnInfo("property_type") val propertyType: String = ""
) : Parcelable
