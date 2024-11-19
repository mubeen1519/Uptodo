package com.stellerbyte.uptodo.services.module

import com.stellerbyte.uptodo.services.implementation.GoogleAuthClient
import com.stellerbyte.uptodo.services.implementation.LogServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@InstallIn(ViewModelComponent :: class)
@Module
abstract class ServiceModule {


    @Binds
    abstract fun provideLogService(impl: LogServiceImpl) : LogService
    @Binds
    abstract fun bindGoogleAuthService(
        googleAuthClient: GoogleAuthClient
    ): GoogleAuthService



}