package com.stellerbyte.uptodo.screens.profile

data class ProfileScreenUseCases(
    val uploadPictureToFirebase: UploadPictureToFirebase,
    val loadProfileFromFirebase: LoadProfileFromFirebase,
    val createOrUpdateProfileToFirebase: CreateOrUpdateProfileToFirebase
)