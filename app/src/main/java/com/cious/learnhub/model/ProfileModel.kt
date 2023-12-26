package com.cious.learnhub.model

import com.google.gson.annotations.SerializedName

data class ProfileModel (


        val city: String,
        val country: String,
        val email: String,
        val id: Int,
        val membership: String,
        val name: String,
        val phoneNumber: String,
        val profileUrl: String,
        val role: String

)