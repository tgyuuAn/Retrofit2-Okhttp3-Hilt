package com.example.retrofit2_okhttp3.data

import android.util.Log
import com.example.retrofit2_okhttp3.data.model.UserResponse
import com.example.retrofit2_okhttp3.data.remotedatasource.UserRemoteDataSource
import com.example.retrofit2_okhttp3.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) :
    UserRepository {
    override suspend fun getUser(id: Int): Flow<Result<UserResponse>> = channelFlow {
        Log.d("tgyuu","UserRepositoryImpl : 함수진입")
        userRemoteDataSource.getUser(id).map {
            it.fold(onSuccess = { Result.success(it) }, onFailure = { Result.failure(it) })
        }.collect {
            send(it)
        }
    }

}