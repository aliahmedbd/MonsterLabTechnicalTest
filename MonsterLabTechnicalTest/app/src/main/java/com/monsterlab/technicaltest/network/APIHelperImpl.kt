package com.monsterlab.technicaltest.network

import com.bracbank.digitallanding.retrofit.RetrofitClient
import com.monsterlab.technicaltest.model.ImagesModel

class APIHelperImpl : APIHelper {

    private var apiService: APIInterface = RetrofitClient.apiInterface
    override suspend fun getImageList(page: Int?): List<ImagesModel> {
        return apiService.getImageList(page)
    }

}