package com.monsterlab.technicaltest.network

import com.bracbank.digitallanding.retrofit.RetrofitClient
import com.monsterlab.technicaltest.model.Images
import retrofit2.Call

class APIHelperImpl : APIHelper {

    private var apiService: APIInterface = RetrofitClient.apiInterface
    override fun getImageList(page: Int?, limit: Int?): Call<List<Images>> {
        return apiService.getImageList(page, limit)
    }


}