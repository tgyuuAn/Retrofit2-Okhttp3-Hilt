package com.example.retrofit2_okhttp3.data.di

import com.example.retrofit2_okhttp3.data.UserRepositoryImpl
import com.example.retrofit2_okhttp3.data.remotedatasource.ReqresApi
import com.example.retrofit2_okhttp3.data.remotedatasource.UserRemoteDataSource
import com.example.retrofit2_okhttp3.data.remotedatasource.UserRemoteDataSourceImpl
import com.example.retrofit2_okhttp3.domain.GetUserUsecase
import com.example.retrofit2_okhttp3.domain.UserRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

const val REQRES_BASEURL = "https://reqres.in/"

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    /**
    @Provides
    @Singleton
    @AccessToken
    fun provideOkHttpClientWithAccessToken(): OkHttpClient {
        return OkHttpClient.Builder().run {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            addInterceptor { chain ->
                val newRequest =
                    chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer ${여기 액세스 토큰}").build()
                chain.proceed(newRequest)
            }
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(15, TimeUnit.SECONDS)
            callTimeout(10, TimeUnit.SECONDS)
            build()
        }
    }

    @Provides
    @Singleton
    @RefreshToken
    fun provideOkHttpClientWithRefreshToken(): OkHttpClient {
        return OkHttpClient.Builder().run {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            addInterceptor { chain ->
                val newRequest =
                    chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer ${여기 리프레시 토큰}").build()
                chain.proceed(newRequest)
            }
            readTimeout(15, TimeUnit.SECONDS)
            connectTimeout(10, TimeUnit.SECONDS)
            callTimeout(10, TimeUnit.SECONDS)
            build()
        }
    }
\
    **/

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().run {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            readTimeout(10, TimeUnit.SECONDS)
            connectTimeout(10, TimeUnit.SECONDS)
            callTimeout(10, TimeUnit.SECONDS)
            build()
        }
    }

    @Provides
    @Singleton
    fun provideReqresApi(okHttpClient: OkHttpClient): ReqresApi =
        Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(REQRES_BASEURL).build().create(ReqresApi::class.java)

    @Provides
    @Singleton
    fun provideUserRemoteDataSoure(reqresApi: ReqresApi): UserRemoteDataSource =
        UserRemoteDataSourceImpl(reqresApi)

    @Provides
    @Singleton
    fun provideUserRepository(userRemoteDataSource: UserRemoteDataSource): UserRepository =
        UserRepositoryImpl(userRemoteDataSource)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AccessToken

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshToken