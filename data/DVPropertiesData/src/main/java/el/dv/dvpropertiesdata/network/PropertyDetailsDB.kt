package el.dv.dvpropertiesdata.network

import androidx.room.Database
import androidx.room.RoomDatabase

const val PROPERTY_DETAILS_DB = "propertyDetailsDB"

@Database(entities = [DaoPropertyDetails::class], version = 1)
abstract class PropertyDetailsDB : RoomDatabase() {
    abstract fun getPropertyDetailsDao()
}
