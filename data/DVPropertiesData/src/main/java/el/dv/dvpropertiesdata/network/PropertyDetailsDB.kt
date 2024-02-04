package el.dv.dvpropertiesdata.network

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import el.dv.dvpropertiesdata.util.TypeConverter

const val PROPERTY_DETAILS_DB = "propertyDetailsDB"

@Database(entities = [DaoPropertyDetails::class], version = 2)
abstract class PropertyDetailsDB : RoomDatabase() {
    abstract fun getPropertyDetailsDao(): PropertyDetailsDao
}
