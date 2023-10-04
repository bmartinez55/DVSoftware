package el.dv.data.dvproperties.network

import androidx.room.Database
import androidx.room.RoomDatabase
import el.dv.domain.dvproperties.propertydetails.PropertyDetails

const val PROPERTY_DETAILS_DB = "propertyDetailsDB"

@Database(entities = [PropertyDetails::class], version = 1)
abstract class PropertyDetailsDB : RoomDatabase() {
    abstract fun getPropertyDetailsDao()
}
