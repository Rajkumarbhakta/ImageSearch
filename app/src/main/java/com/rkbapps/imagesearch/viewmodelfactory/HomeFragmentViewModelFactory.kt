package com.rkbapps.imagesearch.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rkbapps.imagesearch.repository.HomeFragmentRepository
import com.rkbapps.imagesearch.viewmodels.HomeFragmentViewModel

class HomeFragmentViewModelFactory(private val repository: HomeFragmentRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeFragmentViewModel(repository) as T
    }
}