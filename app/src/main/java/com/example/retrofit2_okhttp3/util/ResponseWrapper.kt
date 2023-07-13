package com.example.retrofit2_okhttp3.util

import android.util.Log
import retrofit2.Response

fun <T> Response<T>.onResponse(): Result<T> {
    if (isSuccessful) {
        body()?.let { body ->
            Log.d("tgyuu","onResponse : ${body}")
            return Result.success(body)
        } ?: return Result.failure(Throwable("Body is Null"))
    } else return Result.failure(errorBody()?.string()?.let { Throwable(it) }
        ?: Throwable("Response Error"))
}