package el.dv.fayucafinder.core

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import com.google.android.libraries.places.api.Places
import el.dv.fayucafinder.BuildConfig
import el.dv.fayucafinder.di.FayucaFinderDIModuleProvider
import el.dv.fayucafinder.util.Const
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
            setUpNotificationChannels()
        }
    }

    private fun setUpNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Setup main notification channel for generic notifications
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(
                    NotificationChannel(
                        Const.FAYUCA_FINDER_MAIN_NOTIFICATION_CHANNEL_ID,
                        Const.FAYUCA_FINDER_MAIN_NOTIFICATION_CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_HIGH
                    ).apply {
                        setShowBadge(true)
                        setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), audioAttributes)
                        importance = NotificationManager.IMPORTANCE_HIGH
                        enableVibration(true)
                        description = "Fayuca Finder generic notifications"
                    }
                )
            // Setup notification permission channel for devices running less than Tiramisu
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(
                    NotificationChannel(
                        Const.FAYUCA_FINDER_PERMISSION_NOTIFICATION_CHANNEL_ID,
                        Const.FAYUCA_FINDER_PERMISSION_NOTIFICATION_CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_MIN
                    ).apply {
                        setShowBadge(false)
                        importance = NotificationManager.IMPORTANCE_MIN
                        enableVibration(false)
                        description = "Used  for requesting notification permission for devices under Tiramisu"
                    }
                )
        }
    }
}
