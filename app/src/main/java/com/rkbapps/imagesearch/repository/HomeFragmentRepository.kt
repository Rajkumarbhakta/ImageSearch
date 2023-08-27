package com.rkbapps.imagesearch.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.rkbapps.imagesearch.paging.ImagePagingSource
import com.rkbapps.imagesearch.services.ApiInterface

class HomeFragmentRepository(private val api: ApiInterface) {

    fun getImages() = Pager(
        config = PagingConfig(pageSize = 30, maxSize = 100),
        pagingSourceFactory = { ImagePagingSource(api) }
    ).liveData

}