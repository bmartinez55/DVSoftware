package el.dv.data.di

import el.dv.data.util.NetworkConnectivityMonitorImpl
import el.dv.domain.networkmonitor.NetworkConnectivityMonitor
import org.koin.dsl.module

val commonServiceModule = module {
    single<NetworkConnectivityMonitor> {
        NetworkConnectivityMonitorImpl(connectivityManager = get())
    }
}
