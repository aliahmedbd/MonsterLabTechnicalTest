package com.monsterlab.technicaltest.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.monsterlab.technicaltest.model.Images
import com.monsterlab.technicaltest.network.APIHelper
import com.monsterlab.technicaltest.network.APIInterface

class ImageListPagingSource constructor(private val apiInterface: APIInterface): PagingSource<Int, Images>() {
    override fun getRefreshKey(state: PagingState<Int, Images>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Images> {
        TODO("Not yet implemented")
    }
}