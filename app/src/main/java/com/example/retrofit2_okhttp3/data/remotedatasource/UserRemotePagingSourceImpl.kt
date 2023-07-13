package com.example.retrofit2_okhttp3.data.remotedatasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofit2_okhttp3.data.model.UserResponse
import javax.inject.Inject

class UserRemotePagingSourceImpl @Inject constructor(private val reqresApi: ReqresApi) :
    PagingSource<Int, UserResponse>() {
    override fun getRefreshKey(state: PagingState<Int, UserResponse>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponse> {
        try{
            val nextNumber = params.key ?: 1
            val response = reqresApi.getUesr(nextNumber)
            return LoadResult.Page()
        }
    }

}