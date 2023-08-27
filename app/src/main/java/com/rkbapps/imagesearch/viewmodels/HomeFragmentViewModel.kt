package com.rkbapps.imagesearch.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rkbapps.imagesearch.repository.HomeFragmentRepository

class HomeFragmentViewModel(private val repository:HomeFragmentRepository):ViewModel() {



    val photos = repository.getImages().cachedIn(viewModelScope)

}