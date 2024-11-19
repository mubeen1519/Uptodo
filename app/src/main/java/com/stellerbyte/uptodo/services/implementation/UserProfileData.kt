package com.stellerbyte.uptodo.services.implementation

data class UserProfileData(
    var id : String = "",
    var email : String = "",
    var username : String = "",
    var password : String = "",
    var confirmPassword : String = "",
    var imageUrl : String = ""
)