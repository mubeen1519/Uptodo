package com.stellerbyte.uptodo.services

import android.content.Context
import android.content.SharedPreferences
import com.google.android.datatransport.runtime.scheduling.jobscheduling.SchedulerConfig.Flag
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Constant {
   const val CLIENT_ID = "1007701374527-0k5ii8hhq5hu7sfe56sa89mjt1p2mmce.apps.googleusercontent.com"
}

object SharedPreferencesUtil {
   private const val PREF_NAME = "timer_data"
   private const val KEY_TIMER_PROGRESS = "timer_progress"
   private const val KEY_WEEK_NUMBER = "week_number"
   private const val KEY_ELAPSED_TIME = "elapsed_time"
   private const val KEY_FIRST_TIME_LAUNCH = "first_time_launch"

   lateinit var sharedPreferences: SharedPreferences

   // Initialize sharedPreferences with context
   fun init(context: Context) {
      sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
   }

   // Save progress to SharedPreferences
   fun saveTimerProgress(progress: MutableMap<String, Float>) {
      val editor = sharedPreferences.edit()
      for ((day, value) in progress) {
         editor.putFloat("$KEY_TIMER_PROGRESS$day", value)
      }
      editor.apply()
   }

   fun getTimerProgress(): MutableMap<String, Float> {
      val progress = mutableMapOf<String, Float>()

      val daysOfWeek = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
      for (day in daysOfWeek) {
         val value = sharedPreferences.getFloat("$KEY_TIMER_PROGRESS$day", 0f)
         progress[day] = value
      }
      return progress
   }

   // Clear all timer progress data for a new week
   fun clearTimerProgress() {
      val editor = sharedPreferences.edit()

      val daysOfWeek = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
      for (day in daysOfWeek) {
         editor.remove("$KEY_TIMER_PROGRESS$day")
      }

      editor.apply()
   }

   // Save the current week number
   fun saveWeekNumber(weekNumber: Int) {
      sharedPreferences.edit().putInt(KEY_WEEK_NUMBER, weekNumber).apply()
   }

   // Retrieve the saved week number
   fun getSavedWeekNumber(): Int {
      return sharedPreferences.getInt(KEY_WEEK_NUMBER, -1) // Default -1 if no week is saved
   }


   fun saveElapsedTime(elapsedTime: Long) {
      sharedPreferences.edit().putLong(KEY_ELAPSED_TIME, elapsedTime).apply()
   }

   fun getElapsedTime(): Long {
      return sharedPreferences.getLong(KEY_ELAPSED_TIME, 0L)
   }

   // Check if it's the first time launch
   fun isFirstTimeLaunch(): Boolean {
      return sharedPreferences.getBoolean(KEY_FIRST_TIME_LAUNCH, true)
   }

   // Set first-time launch status
   fun setFirstTimeLaunch(isFirstTime: Boolean) {
      sharedPreferences.edit().putBoolean(KEY_FIRST_TIME_LAUNCH, isFirstTime).apply()
   }
}
