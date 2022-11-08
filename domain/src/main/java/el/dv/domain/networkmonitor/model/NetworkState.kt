package el.dv.domain.networkmonitor.model

sealed class NetworkState {
    object Connected : NetworkState()
    object Disconnected : NetworkState()
}
