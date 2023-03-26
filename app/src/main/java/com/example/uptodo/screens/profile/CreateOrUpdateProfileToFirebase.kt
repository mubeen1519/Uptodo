package com.example.uptodo.screens.profile

import com.example.uptodo.services.implementation.UserProfileData
import com.example.uptodo.services.module.ProfileScreenRepo

class CreateOrUpdateProfileToFirebase(
    private val profileScreenRepo: ProfileScreenRepo
) {
    suspend operator fun invoke(user : UserProfileData) = profileScreenRepo.createOrUpdateProfileToFirebase(user = user)
}