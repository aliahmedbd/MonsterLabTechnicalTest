package com.monsterlab.technicaltest.network

import com.monsterlab.technicaltest.model.ImagesModel

interface APIHelper {
    suspend fun getImageList(page : Int?): List<ImagesModel>
}