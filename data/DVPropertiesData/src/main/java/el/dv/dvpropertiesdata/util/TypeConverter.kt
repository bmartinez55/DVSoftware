package el.dv.dvpropertiesdata.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import el.dv.domain.dvproperties.propertydetails.model.ImagePaths

class TypeConverter {
    @TypeConverter
    suspend fun listToJson(list: List<String>): String = Gson().toJson(list)

    @TypeConverter
    suspend fun jsonToListOfStrings(json: String): ImagePaths = Gson().fromJson<ImagePaths>(json)
}

inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object : TypeToken<T>() {}.type)
