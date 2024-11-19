package com.stellerbyte.uptodo.screens.profile

import com.stellerbyte.uptodo.services.module.ProfileScreenRepo

class LoadProfileFromFirebase(
    private val profileScreenRepo: ProfileScreenRepo
) {
    suspend operator fun invoke() = profileScreenRepo.loadProfileFromFirebase()
}