package com.mindorks.example.paging3.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import com.mindorks.example.paging3.data.APIService
import com.mindorks.example.paging3.data.response.Data

class PostDataSource(private val apiService: APIService) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            Log.d("PostDataSource", "page:$currentLoadingPageKey")
            val response = apiService.getListData()
            val responseData = mutableListOf<Data>()
            val data = response.body()?.entries ?: emptyList()
            responseData.addAll(data)
            val nextKey = if (data.isEmpty()) null else currentLoadingPageKey.plus(1)

            return LoadResult.Page(
                data = responseData,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
            Log.d("PostDataSource", "error :$e")
        }
    }

}