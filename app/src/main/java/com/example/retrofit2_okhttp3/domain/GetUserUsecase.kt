package com.example.retrofit2_okhttp3.domain

import android.util.Log
import com.example.retrofit2_okhttp3.data.model.toUser
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class GetUserUsecase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: Int) = channelFlow {
        Log.d("tgyuu","GetUserUseCase : 함수진입")
        userRepository.getUser(id).collectLatest { it ->
            it.fold(onSuccess = { Result.success(it.toUser()) }, onFailure = { Result.failure(it) })
                .also {
                    send(it)
                }
        }
    }
}