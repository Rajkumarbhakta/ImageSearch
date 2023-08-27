package com.rkbapps.imagesearch.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rkbapps.imagesearch.model.Photo
import com.rkbapps.imagesearch.services.ApiInterface

class ImagePagingSource(private val api: ApiInterface) : PagingSource<Int, Photo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val position = params.key ?: 1
            val response = api.getCuratedImages(position)
            LoadResult.Page(
                data = response.body()!!.photos,
                prevKey = if (position == 1) null else position - 1,
                nextKey = response.body()!!.nextPage.toInt()
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
