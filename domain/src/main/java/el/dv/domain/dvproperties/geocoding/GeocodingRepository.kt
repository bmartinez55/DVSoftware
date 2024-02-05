package el.dv.domain.dvproperties.geocoding

import el.dv.domain.core.Geolocation
import el.dv.domain.core.Result

interface GeocodingRepository {
    suspend fun getPropertyCoordinates(address: String): Result<Geolocation>
}
