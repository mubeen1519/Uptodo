package com.example.uptodo.screens.settings

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingModule {
    @Binds
    @Singleton
    abstract fun bindThemeSetting(
        themeSettingImpl: ThemeSettingImpl
    ) : ThemeSetting
}

