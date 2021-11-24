package com.monsterlab.technicaltest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.monsterlab.technicaltest.model.ImagesModel
import com.monsterlab.technicaltest.network.APIHelper
import com.monsterlab.technicaltest.network.APIInterface
import com.monsterlab.technicaltest.repository.ImageListPagingSource
import com.monsterlab.technicaltest.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ali Ahmed, mail: aliahmedaiub@gmail.com
 */
@HiltViewModel
class ImageListViewModel @Inject constructor(private val apiInterface: APIInterface) : ViewModel() {

    val getAllImages: Flow<PagingData<ImagesModel>> =
        Pager(config = PagingConfig(Constants.IMAGE_PAGE_COUNT, enablePlaceholders = false)) {
            ImageListPagingSource(apiInterface)
        }.flow.cachedIn(viewModelScope)

}