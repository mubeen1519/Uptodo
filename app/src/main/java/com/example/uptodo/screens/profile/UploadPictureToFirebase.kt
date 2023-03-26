package com.example.uptodo.screens.profile

import android.net.Uri
import com.example.uptodo.services.module.ProfileScreenRepo

class UploadPictureToFirebase(
    private val profileScreenRepo: ProfileScreenRepo
) {
    suspend operator fun invoke(url : Uri) = profileScreenRepo.uploadPictureToFirebase(url)
}