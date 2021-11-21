package com.monsterlab.technicaltest.network

import com.monsterlab.technicaltest.model.Images
import retrofit2.Call

interface APIHelper {
    fun getImageList(page : Int?, limit : Int?): Call<List<Images>>

}