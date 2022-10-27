package el.dv.fayucafinder.core

import android.app.Application
import el.dv.fayucafinder.di.FayucaFinderDIModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FayucaFinderApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@FayucaFinderApplication)
            modules(modules = FayucaFinderDIModuleProvider.fayucaFinderModule)
        }
    }
}
