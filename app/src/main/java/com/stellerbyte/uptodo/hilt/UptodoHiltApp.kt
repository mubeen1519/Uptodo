package com.stellerbyte.uptodo.hilt

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.stellerbyte.uptodo.services.implementation.Notifications.NotificationUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UptodoHiltApp : Application() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        val notificationChannel = NotificationChannel(
            NotificationUtil.CHANNEL_ID,
            NotificationUtil.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}
