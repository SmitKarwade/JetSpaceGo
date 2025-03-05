package com.example.jetspacego.repository

import com.example.jetspacego.request.SpaceService
import javax.inject.Inject

class SpaceRepository @Inject constructor(val spaceService: SpaceService){
    suspend fun getMissions(limit : Int, offset : Int) = spaceService.getMissions(limit, offset)
}