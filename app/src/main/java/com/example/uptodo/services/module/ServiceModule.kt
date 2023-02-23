package com.example.uptodo.services.module

import com.example.uptodo.services.implementation.AccountServiceImpl
import com.example.uptodo.services.implementation.LogServiceImpl
import com.example.uptodo.services.implementation.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent :: class)
@Module
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl) : AccountService

    @Binds
    abstract fun provideLogService(impl: LogServiceImpl) : LogService

    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl) : StorageService
}