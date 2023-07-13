package com.example.retrofit2_okhttp3.data.model

import com.example.retrofit2_okhttp3.domain.User
import kotlinx.serialization.Serializable

@Serializable
data class UserListResponse(
    val `data`: List<UserResponse>,
    val page: Int,
    val per_page: Int,
    val support: Support,
    val total: Int,
    val total_pages: Int
) {
    @Serializable
    data class UserResponse(
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

fun UserListResponse.UserResponse.toUser() = User(avatar, email, first_name, id, last_name)