package el.dv.dvpropertiesdata.network

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Dao
interface PropertyDetailsDao {
    @Query("SELECT * FROM propertyDetailsTable ORDER BY id ASC")
    fun getAllProperties(): List<DaoPropertyDetails>

    @Query("SELECT * FROM propertyDetailsTable WHERE property_type LIKE :propertyType")
    fun getAllPropertiesByType(propertyType: String): List<DaoPropertyDetails>

    @Insert
    fun addNewProperty(daoPropertyDetails: DaoPropertyDetails)
}

@Entity(tableName = "propertyDetailsTable")
data class DaoPropertyDetails(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int = 0,
    @ColumnInfo("address") var address: String = "",
    @ColumnInfo("city") var city: String = "",
    @ColumnInfo("state") var state: String = "",
    @ColumnInfo("zip_code") var zipCode: String = "",
    @ColumnInfo("property_cost") var propertyCost: String = "",
    @ColumnInfo("lot_size") var lotSize: String = "",
    @ColumnInfo("property_size") var propertySize: String = "",
    @ColumnInfo("build_date") var buildDate: String = "",
    @ColumnInfo("bedroom_count") var bedroomCount: String = "",
    @ColumnInfo("bathroom_count") var bathroomCount: String = "",
    @ColumnInfo("property_type") var propertyType: String = ""
)
