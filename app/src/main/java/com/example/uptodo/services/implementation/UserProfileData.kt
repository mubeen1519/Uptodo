package com.example.uptodo.services.implementation

data class UserProfileData(
    var id : String? = null,
    var username : String? = null,
    var password : String? = null,
    var confirmPassword : String? = null,
    var imageUrl : String? = null
)