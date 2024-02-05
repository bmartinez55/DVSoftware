package el.dv.domain.dvproperties.geocoding.usecase

import el.dv.domain.core.Geolocation
import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.domain.dvproperties.geocoding.GeocodingRepository

class GetPropertyCoordinatesUseCase(private val geocodingRepository: GeocodingRepository) : SuspendUseCase<String, Result<Geolocation>> {
    override suspend fun run(address: String): Result<Geolocation> {
        return geocodingRepository.getPropertyCoordinates(address)
    }
}
