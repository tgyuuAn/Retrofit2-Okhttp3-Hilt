package com.example.retrofit2_okhttp3.presentation

import com.example.retrofit2_okhttp3.domain.User

sealed class UserState {
        object Loading : UserState()
        object Init : UserState()
        data class Success(val data: User) : UserState()
        data class Error(val error: Throwable?) : UserState()
    }