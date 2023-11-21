package el.dv.dvproperties.core

import android.app.Application
import el.dv.dvproperties.di.DVPropertiesDIModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DVPropertiesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DVPropertiesApplication)
            modules(DVPropertiesDIModuleProvider.dvPropertiesModuleList)
        }
    }
}
