package el.dv.dvpropertiesdata.network

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query

@Dao
interface PropertyDetailsDao {
    @Query("SELECT * FROM propertyDetailsTable ORDER BY id ASC")
    fun getAllProperties(): List<DaoPropertyDetails>

    @Query("SELECT * FROM propertyDetailsTable WHERE property_type LIKE :propertyType")
    fun getAllPropertiesByType(propertyType: String): List<DaoPropertyDetails>
}

@Entity(tableName = "propertyDetailsTable")
data class DaoPropertyDetails(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int,
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
)
