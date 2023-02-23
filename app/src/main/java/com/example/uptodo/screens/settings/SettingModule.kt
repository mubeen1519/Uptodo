package com.example.uptodo.screens.settings

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
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