package com.monsterlab.technicaltest.network

import com.monsterlab.technicaltest.model.Images
import retrofit2.Call

interface APIHelper {
    suspend fun getImageList(page : Int?): List<Images>
}