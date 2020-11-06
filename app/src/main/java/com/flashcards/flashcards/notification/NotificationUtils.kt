package com.flashcards.flashcards.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.flashcards.flashcards.R
import com.flashcards.flashcards.ui.main.MainActivity
import com.flashcards.flashcards.util.ifNullOrEmpty

object NotificationUtils {

    private const val NOTIFICATION_ID_DEMO_1 = 11
    private const val NOTIFICATION_ID_DEMO_2 = 22

    fun showDemoNotification1(
        context: Context,
        title: String,
        content: String,
        pendingIntent: PendingIntent? = null
    ) {
        showNotification(context, title, content, DEFAULT_CHANNEL, NOTIFICATION_ID_DEMO_1, pendingIntent)
    }

    fun showDemoNotification2(
        context: Context,
        title: String,
        content: String,
        pendingIntent: PendingIntent? = null
    ) {
        showNotification(context, title, content, CHANNEL_UPLOAD, NOTIFICATION_ID_DEMO_2, pendingIntent)
    }

    private fun showNotification(
        context: Context,
        title: String,
        content: String,
        ntfChannel: Channel,
        notificationId: Int = 1,
        pendingIntent: PendingIntent? = null
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!isChannelAvailable(context, ntfChannel)) {
                createChannel(context, ntfChannel)
            }
        }

        var lPendingIntent = pendingIntent
        if (lPendingIntent == null) {
            val intent = Intent(context, MainActivity::class.java)
            lPendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        }

        val notificationBuilder = NotificationCompat.Builder(context, ntfChannel.getId())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title.ifNullOrEmpty(context.getString(R.string.app_name)))
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            .setContentText(content)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setSound(getNotificationSoundUri(context, ntfChannel), AudioManager.STREAM_NOTIFICATION)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setContentIntent(lPendingIntent)
            .setLights(Color.BLUE, 500, 1000)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(notificationId, notificationBuilder)
    }

    private fun isChannelAvailable(context: Context, ntfChannel: Channel): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = context.getSystemService(NotificationManager::class.java)
                .getNotificationChannel(ntfChannel.getId())
            channel != null
        } else {
            false
        }
    }

    fun createChannel(context: Context, ntfChannel: Channel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ntfChannel.getId(), ntfChannel.getName(), NotificationManager.IMPORTANCE_HIGH)
            channel.description = ntfChannel.getDescription()
            channel.setShowBadge(true)
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            channel.lightColor = Color.BLUE

            context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    private fun getNotificationSoundUri(context: Context, ntfChannel: Channel): Uri? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = context.getSystemService(NotificationManager::class.java)
                .getNotificationChannel(ntfChannel.getId())
            channel?.sound ?: Settings.System.DEFAULT_NOTIFICATION_URI
        } else {
            Settings.System.DEFAULT_NOTIFICATION_URI
        }
    }

    val DEFAULT_CHANNEL: Channel = ChannelDefault()
    val CHANNEL_UPLOAD: Channel = ChannelUpload()

    abstract class Channel {
        abstract fun getId(): String
        abstract fun getName(): String
        abstract fun getDescription(): String
    }

    private class ChannelDefault : Channel() {
        override fun getId() = ID
        override fun getName() = NAME
        override fun getDescription() = DESCRIPTION

        companion object {
            private const val ID = "channeldefault"
            private const val NAME = "Notification"
            private const val DESCRIPTION = "Description"
        }
    }

    private class ChannelUpload : Channel() {
        override fun getId() = ID
        override fun getName() = NAME
        override fun getDescription() = DESCRIPTION

        companion object {
            private const val ID = "channelupload"
            private const val NAME = "Uploading"
            private const val DESCRIPTION = "Uploading file"
        }
    }
}
