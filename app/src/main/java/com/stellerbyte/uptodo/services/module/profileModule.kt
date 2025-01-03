package com.stellerbyte.uptodo.services.module

import com.stellerbyte.uptodo.screens.profile.CreateOrUpdateProfileToFirebase
import com.stellerbyte.uptodo.screens.profile.LoadProfileFromFirebase
import com.stellerbyte.uptodo.screens.profile.ProfileScreenUseCases
import com.stellerbyte.uptodo.screens.profile.UploadPictureToFirebase
import com.stellerbyte.uptodo.services.implementation.ProfileScreenImp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides fun firestore(): FirebaseFirestore = Firebase.firestore
    @Provides
    fun provideFirebaseAuthInstance() = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseStorageInstance() = FirebaseStorage.getInstance()

    @Provides
    fun provideFirebaseDatabaseInstance() = FirebaseDatabase.getInstance()
    @Provides
    fun provideProfileScreenRepository(
        auth: FirebaseAuth,
        database: FirebaseFirestore,
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