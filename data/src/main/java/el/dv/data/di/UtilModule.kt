package el.dv.data.di

import android.app.NotificationManager
import android.content.Context
import android.location.LocationManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilModule = module {
    single {
        androidContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    single {
        androidContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}
