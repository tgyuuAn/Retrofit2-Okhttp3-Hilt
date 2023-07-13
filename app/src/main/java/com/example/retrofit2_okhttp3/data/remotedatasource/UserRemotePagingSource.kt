package com.example.retrofit2_okhttp3.data.remotedatasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofit2_okhttp3.data.model.UserListResponse
import javax.inject.Inject

class UserRemotePagingSource @Inject constructor(private val reqresApi: ReqresApi) :
    PagingSource<Int, UserListResponse.UserResponse>() {
    override fun getRefreshKey(state: PagingState<Int, UserListResponse.UserResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserListResponse.UserResponse> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = reqresApi.getUserList(nextPageNumber)
            return LoadResult.Page(
                data = response.body()!!.data,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}