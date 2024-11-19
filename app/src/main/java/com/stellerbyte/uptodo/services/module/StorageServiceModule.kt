package com.stellerbyte.uptodo.services.module

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.stellerbyte.uptodo.services.implementation.AccountServiceImpl
import com.stellerbyte.uptodo.services.implementation.GoogleAuthClient
import com.stellerbyte.uptodo.services.implementation.StorageServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object StorageServiceModule {

    @Provides
    @Singleton
    fun provideStorageService(impl: StorageServiceImpl) : StorageService{
        return impl
    }

    @Provides
    @Singleton
    fun provideAccountService(impl: AccountServiceImpl) : AccountService{
        return impl
    }

    @Provides
    @Singleton
    fun provideGoogleAuthClient(
        @ApplicationContext context: Context,
        firebaseAuth: FirebaseAuth
    ): GoogleAuthClient {
        return GoogleAuthClient(context, firebaseAuth)
    }

}