package com.cious.learnhub.data.network.response.customer

data class AddCostumerBody(
    val placeOfBirth: String = "",
    val treatmentLocation: List<String> = listOf(),
    val fullName: String = "",
    val weight: String = "",
    val dateOfBirth: String = "",
    val parentPhoneNumber: String = "",
    val patientType: String = "",
    val residence: String = "",
    val addedById: String = "",
    val height: String = ""
)
