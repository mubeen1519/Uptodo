package com.example.uptodo.screens.settings

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.uptodo.mainViewModel.MainViewModel
import com.example.uptodo.services.module.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    logService: LogService,
) : MainViewModel(logService) {

    private val _locale = MutableStateFlow(Locale.getDefault())
    val locale: StateFlow<Locale> = _locale

    fun onLanguageSelected(locale: Locale) {
        _locale.value = locale
    }
}