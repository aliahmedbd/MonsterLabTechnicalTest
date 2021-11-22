package com.monsterlab.technicaltest.network

import com.monsterlab.technicaltest.model.Images
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIInterface {

    @GET(URL.GET_IMAGE_LIST)
    suspend fun getImageList(
        @Query("page") page: Int?,
    ): List<Images>
}