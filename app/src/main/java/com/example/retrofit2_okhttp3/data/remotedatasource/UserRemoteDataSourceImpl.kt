package com.example.retrofit2_okhttp3.data.remotedatasource

import android.util.Log
import com.example.retrofit2_okhttp3.data.model.UserResponse
import com.example.retrofit2_okhttp3.util.onResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val reqresApi: ReqresApi) :
    UserRemoteDataSource {
    override suspend fun getUser(id: Int): Flow<Result<UserResponse>> = channelFlow {
        Log.d("tgyuu", "UserRemoteDataSourceImpl : 함수진입")
        reqresApi.getUesr(id).onResponse()
            .fold(onSuccess = { Result.success(it) }, onFailure = { Result.failure(it) })
            .also {
                Log.d("tgyuu","UserRemoteDataSourceImpl : 호출완료" + it.toString())
                send(it)
            }
    }
}