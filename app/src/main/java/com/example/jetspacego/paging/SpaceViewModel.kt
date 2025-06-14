package com.example.jetspacego.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetspacego.model.launches.Results
import com.example.jetspacego.request.SpaceService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SpaceViewModel @Inject constructor(private val apiService: SpaceService) : ViewModel(){
    fun getLaunchFlow(
        searchQuery: String?,
        windowStartAfter: String? = null,
        windowEndBefore: String? = null,
        ordering: String = "-net",
        limit: Int = 11
    ): Flow<PagingData<Results>> {
        return Pager(PagingConfig(pageSize = limit)) {
            SpaceDataSource(api = apiService, windowStartAfter = windowStartAfter, windowEndBefore = windowEndBefore, ordering = ordering, limit = limit, searchQuery = searchQuery)
        }.flow.cachedIn(viewModelScope)
    }

}