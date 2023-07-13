package com.example.retrofit2_okhttp3.data.remotedatasource

import com.example.retrofit2_okhttp3.data.model.UserResponse
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    suspend fun getUser(id : Int) : Flow<Result<UserResponse>>
}