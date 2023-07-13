package com.example.retrofit2_okhttp3.data.model

import com.example.retrofit2_okhttp3.domain.User
import kotlinx.serialization.Serializable


@Serializable
data class UserResponse(
    val `data`: Data,
    val support: Support
) {

    @Serializable
    data class Data(
        val avatar: String,
        val email: String,
        val first_name: String,
        val id: Int,
        val last_name: String
    )

    @Serializable
    data class Support(
        val text: String,
        val url: String
    )
}

fun UserResponse.toUser() =
    User(`data`.avatar, `data`.email, `data`.first_name, `data`.id, `data`.last_name)