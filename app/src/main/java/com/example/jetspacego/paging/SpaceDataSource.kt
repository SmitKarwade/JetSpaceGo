package com.example.jetspacego.paging

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetspacego.model.launches.Results
import com.example.jetspacego.request.SpaceService

class SpaceDataSource(
    private val api: SpaceService,
    private val windowStartAfter: String?,
    private val windowEndBefore: String?,
    private val ordering: String,
    private val limit: Int,
    private val searchQuery: String?
) : PagingSource<Int, Results>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        return try {
            Log.d("Paging", "Loading data with key: ${params.key}")

            val currentOffset = params.key ?: 0
            val response = api.getMissions(ordering = ordering,
                windowStartAfter = windowStartAfter,
                windowEndBefore = windowEndBefore,
                limit = limit,
                offset = currentOffset,
                search = searchQuery)
            Log.d("Paging", "Received ${response.results.size} items")


            val nextOffset = response.next?.let { nextUrl ->
                parseOffsetFromUrl(nextUrl)
            }

            val prevOffset = response.previous?.let { prevUrl ->
                if (prevUrl.contains("offset=")) {
                    parseOffsetFromUrl(prevUrl)
                } else {
                    0
                }
            }

            Log.d("Paging", "Current offset: $currentOffset, Next offset: $nextOffset, Prev offset: $prevOffset")

            LoadResult.Page(
                data = response.results,
                prevKey = prevOffset,
                nextKey = nextOffset
            )

        } catch (e: Exception) {
            Log.e("Paging", "Error loading data", e)
            LoadResult.Error(e)
        }
    }

    private fun parseOffsetFromUrl(url: String): Int? {
        return try {
            val uri = Uri.parse(url)
            uri.getQueryParameter("offset")?.toIntOrNull()
        } catch (e: Exception) {
            Log.e("Paging", "Error parsing offset from URL: $url ($e)")
            null
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(10)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(10)
        }
    }
}