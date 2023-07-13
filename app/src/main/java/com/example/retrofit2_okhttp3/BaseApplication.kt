package com.example.retrofit2_okhttp3

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    private fun log(str : String) = Log.d("tgyuu",str)
}