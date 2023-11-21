package el.dv.presentation.view.manager.notification

import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import android.app.NotificationManager as SystemNotificationManager

interface NotificationManager {

    fun showNotification(
        notificationId: Int,
        channelId: String,
        @DrawableRes smallIconId: Int,
        @DrawableRes largeIconId: Int,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        title: String = "",
        description: String = "",
        autoCancelEnabled: Boolean = false,
        onGoingEnabled: Boolean = false,
        pendingIntent: PendingIntent? = null,
        style: NotificationStyle = NotificationStyle.Default
    )

    fun cancelNotification(notificationId: Int)
}

class NotificationManagerImpl(
    private val notificationManager: SystemNotificationManager,
    private val context: Context
) : NotificationManager {

    override fun showNotification(
        notificationId: Int,
        channelId: String,
        smallIconId: Int,
        largeIconId: Int,
        priority: Int,
        title: String,
        description: String,
        autoCancelEnabled: Boolean,
        onGoingEnabled: Boolean,
        pendingIntent: PendingIntent?,
        style: NotificationStyle
    ) {
        when (style) {
            NotificationStyle.Default -> {
                notificationManager.notify(
                    notificationId,
                    getDefaultStyleNotification(
                        channelId,
                        smallIconId,
                        largeIconId,
                        priority,
                        title,
                        description,
                        autoCancelEnabled,
                        onGoingEnabled,
                        pendingIntent
                    ).build()
                )
            }
            NotificationStyle.BigPictureStyle -> {
                notificationManager.notify(
                    notificationId,
                    getBigPictureStyleNotification(
                        channelId,
                        smallIconId,
                        largeIconId,
                        priority,
                        title,
                        autoCancelEnabled,
                        onGoingEnabled,
                        pendingIntent
                    ).build()
                )
            }
            NotificationStyle.BigTextStyle -> {
                notificationManager.notify(
                    notificationId,
                    getBigTextStyleNotification(
                        channelId,
                        smallIconId,
                        largeIconId,
                        priority,
                        title,
                        description,
                        autoCancelEnabled,
                        onGoingEnabled,
                        pendingIntent
                    ).build()
                )
            }
            else -> {}
        }
    }

    override fun cancelNotification(notificationId: Int) {
        notificationManager.cancel(notificationId)
    }

    private fun getDefaultStyleNotification(
        channelId: String,
        smallIconId: Int,
        largeIconId: Int,
        priority: Int,
        title: String,
        description: String,
        autoCancelEnabled: Boolean,
        onGoingEnabled: Boolean,
        pendingIntent: PendingIntent?
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(smallIconId)
            setLargeIcon(BitmapFactory.decodeResource(context.resources, largeIconId))
            setPriority(priority)
            setContentTitle(title)
            setContentText(description)
            setAutoCancel(autoCancelEnabled)
            setOngoing(onGoingEnabled)
            setContentIntent(pendingIntent)
        }
    }

    private fun getBigPictureStyleNotification(
        channelId: String,
        smallIconId: Int,
        bigPictureId: Int,
        priority: Int,
        title: String,
        autoCancelEnabled: Boolean,
        onGoingEnabled: Boolean,
        pendingIntent: PendingIntent?
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(smallIconId)
            setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(context.resources, bigPictureId))
                    .setBigContentTitle(title)
            )
            setPriority(priority)
            setAutoCancel(autoCancelEnabled)
            setOngoing(onGoingEnabled)
            setContentIntent(pendingIntent)
        }
    }

    private fun getBigTextStyleNotification(
        channelId: String,
        smallIconId: Int,
        largeIconId: Int,
        priority: Int,
        title: String,
        description: String,
        autoCancelEnabled: Boolean,
        onGoingEnabled: Boolean,
        pendingIntent: PendingIntent?
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(smallIconId)
            setLargeIcon(BitmapFactory.decodeResource(context.resources, largeIconId))
            setContentTitle(title)
            setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(description)
            )
            setPriority(priority)
            setAutoCancel(autoCancelEnabled)
            setOngoing(onGoingEnabled)
            setContentIntent(pendingIntent)
        }
    }
}

enum class NotificationStyle {
    Default,
    BigTextStyle,
    InboxStyle,
    BigPictureStyle,
    MessagingStyle,
    DecoratedCustomViewStyle
}
