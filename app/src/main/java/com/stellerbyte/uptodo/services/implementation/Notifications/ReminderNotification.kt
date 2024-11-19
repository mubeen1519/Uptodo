package com.stellerbyte.uptodo.services.implementation.Notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.stellerbyte.uptodo.R

class ReminderNotification(private val context: Context) {

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun sendReminderNotification(title: String?) {
        val notification = NotificationCompat.Builder(context, NotificationUtil.CHANNEL_ID)
            .setContentText(context.getString(R.string.app_name))
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_logo)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("It's time for $title")
            )
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NotificationUtil.Noti_ID, notification)
    }
}