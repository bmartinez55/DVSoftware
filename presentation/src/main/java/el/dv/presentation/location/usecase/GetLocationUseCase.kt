package el.dv.presentation.location.usecase

import el.dv.domain.core.SuspendUseCase
import el.dv.domain.location.LocationManager
import el.dv.domain.location.LocationState
import kotlinx.coroutines.flow.Flow

class GetLocationUseCase(private val locationManager: LocationManager) : SuspendUseCase<Unit, Flow<LocationState>> {

    override suspend fun run(param: Unit): Flow<LocationState> {
        return locationManager.getLocation()
    }
}
