package com.monsterlab.technicaltest.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.monsterlab.technicaltest.model.Images
import com.monsterlab.technicaltest.network.APIHelper
import com.monsterlab.technicaltest.network.APIInterface
import com.monsterlab.technicaltest.utils.Constants
import retrofit2.HttpException
import java.io.IOException

class ImageListPagingSource constructor(private val apiInterface: APIInterface) :
    PagingSource<Int, Images>() {
    override fun getRefreshKey(state: PagingState<Int, Images>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Images> {
        val pageIndex = params.key ?: Constants.IMAGE_PAGE_COUNT
        return try {
            val response = apiInterface.getImageList(page = pageIndex)
            LoadResult.Page(
                response,
                prevKey = if (pageIndex == Constants.IMAGE_PAGE_COUNT) null else pageIndex - 1,
                nextKey = if (response.isEmpty()) null else pageIndex + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}