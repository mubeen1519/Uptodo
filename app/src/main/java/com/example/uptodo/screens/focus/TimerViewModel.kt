package com.example.uptodo.screens.focus

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.uptodo.components.patterns.Utility
import com.example.uptodo.components.patterns.Utility.formatTime
import com.example.uptodo.mainViewModel.MainViewModel
import com.example.uptodo.services.module.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    logService: LogService
) : MainViewModel(logService) {
    private  val _timerState = MutableLiveData(Utility.TIME_COUNTDOWN.formatTime())
    val timeState : LiveData<String> = _timerState

    private val _progress = MutableLiveData(1.00F)
    val progress: LiveData<Float> = _progress

    private val _isStarted = MutableLiveData(false)
    val isStarted: LiveData<Boolean> = _isStarted

    var countDown : CountDownTimer? = null

    fun handleCountDownTimer(){
        if(isStarted.value == true){
            pauseTimer()
        }else {
            startTime()
        }
    }

    fun startTime(){
        countDown = object : CountDownTimer(Utility.TIME_COUNTDOWN,1000){
            override fun onTick(minutes: Long) {
                val progressValue = minutes.toFloat() / Utility.TIME_COUNTDOWN
                handleTimeValues(true,minutes.formatTime(),progressValue)

            }

            override fun onFinish() {
                pauseTimer()

            }
        }
        countDown?.start()
    }

    private fun pauseTimer(){
        countDown?.cancel()
        handleTimeValues(false, Utility.TIME_COUNTDOWN.formatTime(),1.0f)

    }

    fun handleTimeValues(isStarted : Boolean, text : String , progress : Float){
        _isStarted.value = isStarted
        _timerState.value = text
        _progress.value = progress
    }
}