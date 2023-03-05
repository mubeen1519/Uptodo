package com.example.uptodo.screens.profile

import com.example.uptodo.services.module.AccountService
import com.example.uptodo.services.module.ProfileScreenRepo

class LoadProfileFromFirebase(
    private val profileScreenRepo: ProfileScreenRepo
) {
    suspend operator fun invoke() = profileScreenRepo.loadProfileFromFirebase()
}