package com.example.retrofit2_okhttp3.domain

import com.example.retrofit2_okhttp3.data.model.UserResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(id : Int) : Flow<Result<UserResponse>>
}