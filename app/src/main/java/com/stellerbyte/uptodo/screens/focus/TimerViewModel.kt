package com.stellerbyte.uptodo.screens.focus

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stellerbyte.uptodo.components.patterns.Utility
import com.stellerbyte.uptodo.components.patterns.Utility.formatTime
import com.stellerbyte.uptodo.mainViewModel.MainViewModel
import com.stellerbyte.uptodo.services.SharedPreferencesUtil
import com.stellerbyte.uptodo.services.module.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    logService: LogService
) : MainViewModel(logService) {

    private val _timerState = MutableLiveData(Utility.TIME_COUNTDOWN.formatTime())
    val timeState: LiveData<String> = _timerState

    private val _elapsedTimeMillis = MutableLiveData(0L) // Track the elapsed time in milliseconds
    val elapsedTimeMillis: LiveData<Long> = _elapsedTimeMillis

    private val _progress = MutableLiveData(1.00F)
    val progress: LiveData<Float> = _progress

    private val _isStarted = MutableLiveData(false)
    val isStarted: LiveData<Boolean> = _isStarted

    private var countDown: CountDownTimer? = null
    private var countdownTimeInMillis: Long = Utility.TIME_COUNTDOWN // Default value
    // Variable to hold the saved timer progress (e.g., for the current day of the week)
    var savedProgress: MutableMap<String, Float> = mutableMapOf()

    init {
        // Load saved progress from SharedPreferences when the ViewModel is initialized
        resetProgressIfNewWeek()
        savedProgress = loadProgress()
        _elapsedTimeMillis.value = SharedPreferencesUtil.getElapsedTime()

    }

    fun handleCountDownTimer(selectedDurationMillis: Long) {
        if (isStarted.value == true) {
            pauseTimer()
        } else {
            startTime(selectedDurationMillis)
        }
    }

    fun startTime(durationMillis: Long) {
        countDown?.cancel()  // Cancel any existing timer

        countDown = object : CountDownTimer(durationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val elapsedMillis = durationMillis - millisUntilFinished
                _elapsedTimeMillis.value = elapsedMillis.coerceAtLeast(0)

                val progressValue = elapsedMillis.toFloat() / durationMillis
                _progress.value = progressValue

                Log.d("TimerProgress", "OnTick: elapsedTimeMillis = $elapsedMillis, progress = $progressValue")
                handleTimeValues(true, millisUntilFinished.formatTime(), progressValue)
            }

            override fun onFinish() {
                pauseTimer()
            }
        }
        countDown?.start()
    }

    private fun pauseTimer() {
        countDown?.cancel()
        saveProgress()
        SharedPreferencesUtil.saveElapsedTime(_elapsedTimeMillis.value ?: 0L)
        handleTimeValues(false, countdownTimeInMillis.formatTime(), 1.0f)
    }

    private fun handleTimeValues(isStarted: Boolean, text: String, progress: Float) {
        _isStarted.value = isStarted
        _timerState.value = text
        _progress.value = progress
    }
    // Load saved progress from SharedPreferences
    private fun loadProgress(): MutableMap<String, Float> {
        return SharedPreferencesUtil.getTimerProgress() ?: mutableMapOf()
    }

    // Save the current progress in SharedPreferences
    private fun saveProgress() {
        val dayOfWeek = getDayOfWeek() // Get the current day, e.g., "Mon", "Tue", etc.

        // Get the existing progress for today and add the new progress
        val currentProgress = savedProgress[dayOfWeek] ?: 0f
        savedProgress[dayOfWeek] = currentProgress + (_progress.value ?: 0f)

        // Save the accumulated progress for today
        SharedPreferencesUtil.saveTimerProgress(savedProgress)
    }

    private fun resetProgressIfNewWeek() {
        val currentWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)
        val savedWeek = SharedPreferencesUtil.getSavedWeekNumber() // Implement this method in SharedPreferencesUtil

        if (currentWeek != savedWeek) {
            // Clear all progress data
            savedProgress.clear()
            SharedPreferencesUtil.clearTimerProgress() // Clears progress in SharedPreferences

            // Save the new week number
            SharedPreferencesUtil.saveWeekNumber(currentWeek)
        }
    }


    // Helper function to get the current day of the week
    private fun getDayOfWeek(): String {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return when (dayOfWeek) {
            Calendar.SUNDAY -> "SUN"
            Calendar.MONDAY -> "MON"
            Calendar.TUESDAY -> "TUE"
            Calendar.WEDNESDAY -> "WED"
            Calendar.THURSDAY -> "THU"
            Calendar.FRIDAY -> "FRI"
            Calendar.SATURDAY -> "SAT"
            else -> "SUN"
        }
    }
}