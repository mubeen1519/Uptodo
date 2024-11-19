package com.stellerbyte.uptodo.screens.profile

import com.stellerbyte.uptodo.services.implementation.UserProfileData
import com.stellerbyte.uptodo.services.module.ProfileScreenRepo

class CreateOrUpdateProfileToFirebase(
    private val profileScreenRepo: ProfileScreenRepo
) {
    suspend operator fun invoke(user : UserProfileData) = profileScreenRepo.createOrUpdateProfileToFirebase(user = user)
}