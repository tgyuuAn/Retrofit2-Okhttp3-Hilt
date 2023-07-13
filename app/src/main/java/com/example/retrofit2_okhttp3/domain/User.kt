package com.example.retrofit2_okhttp3.domain

import kotlinx.serialization.SerialName

data class User(
    val avatar: String,
    val email: String,
    val firstName: String,
    val id: Int,
    val lastName: String
)
