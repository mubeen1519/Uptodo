package com.example.uptodo.screens.settings

import android.content.Context
import androidx.compose.ui.text.intl.Locale


class LanguageRepository(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    var language: String?
        get() = sharedPreferences.getString("language", null)
        set(value) = sharedPreferences.edit().putString("language", value).apply()
}

sealed class Language(val locale: Locale, val displayName : String){
    object English : Language(Locale("en"), "English")
    object French : Language(Locale("fr"), "Français")
    object German : Language(Locale("de"), "Deutsch")

    companion object {
        val supportedLanguages = listOf(English, French, German)
    }
}