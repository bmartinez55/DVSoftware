package el.dv.domain.networkmonitor

import el.dv.domain.networkmonitor.model.NetworkState
import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityMonitor {
    suspend fun monitorNetwork(): Flow<NetworkState>
}
