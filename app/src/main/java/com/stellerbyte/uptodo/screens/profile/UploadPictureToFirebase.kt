package com.stellerbyte.uptodo.screens.profile

import android.net.Uri
import com.stellerbyte.uptodo.services.module.ProfileScreenRepo

class UploadPictureToFirebase(
    private val profileScreenRepo: ProfileScreenRepo
) {
    suspend operator fun invoke(url : Uri) = profileScreenRepo.uploadPictureToFirebase(url)
}