package el.dv.dvpropertiesdata.network.geocoding

import el.dv.dvpropertiesdata.network.geocoding.model.PropertyLocationDetailsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface GeocodingApi {
    @GET
    suspend fun getPropertyCoordinates(@Url url: String): Call<PropertyLocationDetailsDTO>
}
