package com.mindorks.example.paging3.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import com.mindorks.example.paging3.data.APIService
import com.mindorks.example.paging3.data.response.Data

class PostDataSource(private val apiService: APIService) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            Log.d(
                "PostDataSource",
                "page: $currentLoadingPageKey, pageSize = ${params.pageSize}"
            )
            val response = apiService.getListData(currentLoadingPageKey, params.pageSize)
            val responseData = mutableListOf<Data>()
            val data = response.body()
            val listData = data?.myData ?: emptyList()
            Log.d("PostDataSource", "Response size: ${listData.size}")

            responseData.addAll(listData)

            val nextKey = if (currentLoadingPageKey >= (data?.total_pages
                    ?: 1)
            ) null else currentLoadingPageKey.plus(1)

            return LoadResult.Page(
                data = responseData,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
            Log.d("PostDataSource", "$e")
        }
    }

}