package com.example.jetspacego.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetspacego.model.Result
import com.example.jetspacego.request.SpaceService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SpaceViewModel @Inject constructor(private val apiService: SpaceService) : ViewModel(){
    val launchFlow: Flow<PagingData<Result>> = Pager(
        config = PagingConfig(pageSize = 10,
            prefetchDistance = 2,
            initialLoadSize = 10,
            enablePlaceholders = false),
        pagingSourceFactory = { SpaceDataSource(apiService) }
    ).flow.cachedIn(viewModelScope)
}