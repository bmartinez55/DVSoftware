package el.dv.dvpropertiesdata.network.geocoding.repository

import el.dv.domain.core.CoroutineDispatchers
import el.dv.domain.core.Geolocation
import el.dv.domain.core.Result
import el.dv.domain.dvproperties.geocoding.GeocodingRepository
import el.dv.domain.logging.AppLog
import el.dv.dvpropertiesdata.extension.appendUrl
import el.dv.dvpropertiesdata.network.geocoding.GeocodingApi
import el.dv.dvpropertiesdata.network.geocoding.model.LocationCoordinatesDTO
import el.dv.dvpropertiesdata.network.geocoding.model.PropertyLocationDetailsDTO
import kotlinx.coroutines.withContext
import retrofit2.await

class GeocodingRepositoryImpl(private val geocodingApi: GeocodingApi, private val dispatchers: CoroutineDispatchers) : GeocodingRepository {
    override suspend fun getPropertyCoordinates(address: String): Result<Geolocation> = withContext(dispatchers.IO) {
        try {
            val response = geocodingApi.getPropertyCoordinates(address.appendUrl()).await()
            Result.Success(response.toGeolocation())
        } catch (e: Exception) {
            AppLog.e(TAG,"getPropertyCoordinates error", e)
            Result.Failure(e)
        }
    }

    companion object {
        const val TAG = "GeocodingRepositoryImpl"
    }
}

private fun PropertyLocationDetailsDTO.toGeolocation(): Geolocation {
    return this.hits.first().points.first().toGeoLocation()
}

private fun LocationCoordinatesDTO.toGeoLocation(): Geolocation {
    return Geolocation(lat = this.latitude, lon = this.longitude)
}
