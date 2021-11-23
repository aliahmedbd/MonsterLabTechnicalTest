package com.monsterlab.technicaltest.network

import com.monsterlab.technicaltest.model.ImagesModel
import com.monsterlab.technicaltest.network.APIHelper
import com.monsterlab.technicaltest.network.APIInterface
import javax.inject.Inject


class ApiHelperImpl @Inject constructor(private val apiService: APIInterface) : APIHelper {

    override suspend fun getImageList(page: Int?): List<ImagesModel> {
        return apiService.getImageList(page)
    }

}