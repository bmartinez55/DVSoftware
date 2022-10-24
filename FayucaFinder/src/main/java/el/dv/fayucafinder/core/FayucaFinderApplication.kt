package el.dv.fayucafinder.core

import android.app.Application
import org.koin.core.context.startKoin

class FayucaFinderApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

        }
    }
}