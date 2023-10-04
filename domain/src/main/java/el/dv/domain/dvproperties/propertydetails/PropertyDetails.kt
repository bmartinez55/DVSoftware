package el.dv.domain.dvproperties.propertydetails

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "propertyDetailsTable")
data class PropertyDetails(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo("address") val address: String = "",
    @ColumnInfo("city") val city: String = "",
    val state: String = "",
    val zipCode: String = "",
    val propertyValue: String = "",
    val lotSize: String = "",
    val propertySize: String = "",
    val buildDate: String = "",
    val bedroomCount: String = "",
    val bathroomCount: String = "",
    val propertyType: PropertyType = PropertyType.SFH
) : Parcelable

enum class PropertyType {
    SFH,
    Multi,
    Commercial
}
