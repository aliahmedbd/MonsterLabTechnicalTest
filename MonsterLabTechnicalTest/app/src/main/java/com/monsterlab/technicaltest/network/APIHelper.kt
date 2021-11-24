package com.monsterlab.technicaltest.network

import com.monsterlab.technicaltest.model.ImagesModel
/**
 * Created by Ali Ahmed, mail: aliahmedaiub@gmail.com
 */
interface APIHelper {
    suspend fun getImageList(page : Int?): List<ImagesModel>
}