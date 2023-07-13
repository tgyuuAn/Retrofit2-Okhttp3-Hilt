package com.example.retrofit2_okhttp3.data.remotedatasource

import com.example.retrofit2_okhttp3.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresApi {

    @GET(value = "api/users")
    suspend fun getUesr(
        @Query("id") id : Int) : Response<UserResponse>
}