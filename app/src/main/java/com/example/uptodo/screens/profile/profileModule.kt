package com.example.uptodo.screens.profile

import com.example.uptodo.services.implementation.ProfileScreenImp
import com.example.uptodo.services.module.ProfileScreenRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Provides
    fun provideFirebaseAuthInstance() = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseStorageInstance() = FirebaseStorage.getInstance()

    @Provides
    fun provideFirebaseDatabaseInstance() = FirebaseDatabase.getInstance()
    @Provides
    fun provideProfileScreenRepository(
        auth: FirebaseAuth,
        database: FirebaseDatabase,
        storage: FirebaseStorage
    ): ProfileScreenRepo = ProfileScreenImp(auth, database, storage)
    @Provides
    fun provideProfileScreenUseCase(profileScreenRepo: ProfileScreenRepo) =
        ProfileScreenUseCases(
            createOrUpdateProfileToFirebase = CreateOrUpdateProfileToFirebase(
                profileScreenRepo
            ),
            loadProfileFromFirebase = LoadProfileFromFirebase(profileScreenRepo),
            uploadPictureToFirebase = UploadPictureToFirebase(profileScreenRepo)
        )

}