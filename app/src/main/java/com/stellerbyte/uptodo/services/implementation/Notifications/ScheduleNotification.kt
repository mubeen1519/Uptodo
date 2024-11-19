package com.stellerbyte.uptodo.services.implementation.Notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import java.util.Calendar

class ScheduleNotification {

    @RequiresApi(Build.VERSION_CODES.S)
    fun scheduleNotification(
        context: Context,
        dateMillis: Long,
        hour: Int,
        minute: Int,
        title: String
    ) {
        val intent = Intent(context.applicationContext, ReminderReceiver::class.java).apply {
            putExtra(NotificationUtil.Noti_KEY_TITLE, title)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context.applicationContext,
            NotificationUtil.Noti_ID,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create calendar instance and set it to the selected date and time, minus 30 minutes
        val calendar = Calendar.getInstance().apply {
            timeInMillis = dateMillis
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            add(Calendar.MINUTE, -30) // Set reminder 30 minutes earlier
        }

        // Check for API level to safely call `canScheduleExactAlarms()`
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                Toast.makeText(context, "Please allow exact alarms in settings", Toast.LENGTH_LONG).show()
                val intentSet = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                context.startActivity(intentSet)
            }
        } else {
            // For Android versions below API level 31, directly set the alarm
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }

        Toast.makeText(context, "Reminder schedule before half hour remaining", Toast.LENGTH_SHORT).show()
    }
}
