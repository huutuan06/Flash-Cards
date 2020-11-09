package com.flashcards.flashcards.notification.service

import android.content.Context
import com.flashcards.flashcards.R
import com.flashcards.flashcards.notification.NotificationUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class PushNotificationService : FirebaseMessagingService() {

    fun notifyNewOfferArrive(
        context: Context,
        title: String? = null,
        content: String? = null)
    {
        showNotificationOffer(context, title, content)
    }

    private fun showNotificationOffer(
        context: Context,
        title: String? = null,
        content: String? = null)
    {
        NotificationUtils.showDemoNotification2(
            context,
            title ?: context.getString(R.string.offer_title),
            content ?: context.getString(R.string.offer_content),
            null
        )
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Timber.d("onMessageReceived -- ${p0.data}")

        p0.data.let { data ->
            data["id"]?.toLongOrNull()?.let {
                notifyNewOfferArrive(context = this)
            }
        }
        NotificationUtils.showDemoNotification1(
            this, p0.data["title"].toString(), p0.data["content"].toString(),null)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        //TODO
    }
}