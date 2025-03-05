package com.example.jetspacego.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetspacego.model.MissionResponse
import com.example.jetspacego.model.Result
import com.example.jetspacego.request.SpaceService

class SpaceDataSource(
    private val apiService: SpaceService
) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val offset = params.key ?: 0
            val response = apiService.getMissions(limit = 10, offset = offset)

            Log.d("SpaceDataSource", "Response:")

            LoadResult.Page(
                data = response.results,
                prevKey = if (offset == 0) null else offset - 10,
                nextKey = response.next?.let { offset + 10 }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
