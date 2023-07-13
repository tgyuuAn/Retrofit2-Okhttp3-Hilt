package com.example.retrofit2_okhttp3.domain

import android.util.Log
import androidx.paging.map
import com.example.retrofit2_okhttp3.data.model.toUser
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserUsecase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: Int) = channelFlow {
        Log.d("tgyuu", "GetUserUseCase : 함수진입")
        userRepository.getUser(id).collectLatest { it ->
            it.fold(onSuccess = { Result.success(it.toUser()) }, onFailure = { Result.failure(it) })
                .also {
                    send(it)
                }
        }
    }

    fun getUserList() = userRepository.getUserList().let { pager ->
        Log.d("tgyuu", "GetUserUseCase : RV2 함수진입")
        pager.map { pagingData ->
            pagingData.map {
                Log.d("tgyuu", "GetUserUseCase : RV2 "+it.toString())
                Log.d("tgyuu", "GetUserUseCase : RV2 "+it.toUser().toString())
                it.toUser()
            }
        }
    }
}