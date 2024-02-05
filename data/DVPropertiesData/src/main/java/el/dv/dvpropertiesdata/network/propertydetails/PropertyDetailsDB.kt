package el.dv.dvpropertiesdata.network.propertydetails

import androidx.room.Database
import androidx.room.RoomDatabase
import el.dv.dvpropertiesdata.network.propertydetails.DaoPropertyDetails
import el.dv.dvpropertiesdata.network.propertydetails.PropertyDetailsDao

const val PROPERTY_DETAILS_DB = "propertyDetailsDB"

@Database(entities = [DaoPropertyDetails::class], version = 2)
abstract class PropertyDetailsDB : RoomDatabase() {
    abstract fun getPropertyDetailsDao(): PropertyDetailsDao
}
