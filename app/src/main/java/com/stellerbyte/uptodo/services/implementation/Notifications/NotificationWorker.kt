package com.stellerbyte.uptodo.services.implementation.Notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val scheduleNotificationService = context?.let { ReminderNotification(it) }
        val title: String? = intent?.getStringExtra(NotificationUtil.Noti_KEY_TITLE)
        scheduleNotificationService?.sendReminderNotification(title)
    }
}