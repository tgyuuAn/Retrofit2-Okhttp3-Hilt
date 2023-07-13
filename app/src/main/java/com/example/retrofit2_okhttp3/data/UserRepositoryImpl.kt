package com.example.retrofit2_okhttp3.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.retrofit2_okhttp3.data.model.UserListResponse
import com.example.retrofit2_okhttp3.data.model.UserResponse
import com.example.retrofit2_okhttp3.data.remotedatasource.UserRemoteDataSource
import com.example.retrofit2_okhttp3.data.remotedatasource.UserRemotePagingSource
import com.example.retrofit2_okhttp3.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userRemotePagingSource: UserRemotePagingSource
) :
    UserRepository {
    override suspend fun getUser(id: Int): Flow<Result<UserResponse>> = channelFlow {
        Log.d("tgyuu", "UserRepositoryImpl : 함수진입")
        userRemoteDataSource.getUser(id).map {
            it.fold(onSuccess = { Result.success(it) }, onFailure = { Result.failure(it) })
        }.collect {
            send(it)
        }
    }

    override fun getUserList() : Flow<PagingData<UserListResponse.UserResponse>> {
        Log.d("tgyuu", "UserRepositoryImpl : RV2 함수진입")
        return Pager(PagingConfig(pageSize = 12)) { userRemotePagingSource }.flow
    }
}