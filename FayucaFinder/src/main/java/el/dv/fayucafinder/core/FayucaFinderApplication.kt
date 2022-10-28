package el.dv.fayucafinder.core

import android.app.Application
import com.google.android.libraries.places.api.Places
import el.dv.fayucafinder.BuildConfig
import el.dv.fayucafinder.di.FayucaFinderDIModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class FayucaFinderApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@FayucaFinderApplication)
            loadKoinModules(FayucaFinderDIModuleProvider.fayucaFinderModule)
            Places.initialize(applicationContext, BuildConfig.MAP_API_KEY)
        }
    }
}
