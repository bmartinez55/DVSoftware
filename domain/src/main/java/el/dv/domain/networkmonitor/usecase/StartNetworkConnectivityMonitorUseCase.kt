package el.dv.domain.networkmonitor.usecase

import el.dv.domain.core.SuspendUseCase
import el.dv.domain.networkmonitor.NetworkConnectivityMonitor
import el.dv.domain.networkmonitor.model.NetworkState
import kotlinx.coroutines.flow.Flow

class StartNetworkConnectivityMonitorUseCase(private val networkConnectivityMonitor: NetworkConnectivityMonitor) :
    SuspendUseCase<Unit, Flow<NetworkState>> {

    override suspend fun run(param: Unit): Flow<NetworkState> {
        return networkConnectivityMonitor.monitorNetwork()
    }
}
