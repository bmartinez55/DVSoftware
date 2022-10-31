package el.dv.presentation.location.usecase

import el.dv.domain.core.SuspendUseCase
import el.dv.domain.location.LocationManager

class StopLocationUseCase(private val locationManager: LocationManager) : SuspendUseCase<Unit, Unit> {

    override suspend fun run(param: Unit) {
        locationManager.stopLocation()
    }
}
