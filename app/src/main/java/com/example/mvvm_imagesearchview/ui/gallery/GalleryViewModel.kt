package com.example.mvvm_imagesearchview.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mvvm_imagesearchview.data.UnsplashRepositry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


//class GalleryViewModel @ViewModelInject constructor( ViewModelInject class is deprecated
@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repositry: UnsplashRepositry
) :ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val photos = currentQuery.switchMap { queryString ->
        repositry.getSearchResults(queryString).cachedIn(viewModelScope)

    }

    companion object {
        private const val DEFAULT_QUERY = "cats"
    }


}