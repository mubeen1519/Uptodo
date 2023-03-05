package com.example.uptodo.services.implementation

data class UserProfileData(
    var id : String = "",
    var username : String = "",
    var password : String = "",
    var confirmPassword : String = "",
    var imageUrl : String = ""
)