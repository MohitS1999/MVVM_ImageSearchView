package com.example.mvvm_imagesearchview.ui.gallery

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.mvvm_imagesearchview.data.UnsplashRepositry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


//class GalleryViewModel @ViewModelInject constructor( ViewModelInject class is deprecated
@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repositry: UnsplashRepositry,
    state:SavedStateHandle
) :ViewModel() {

    // this is how we can save the state
    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val photos = currentQuery.switchMap { queryString ->
        repositry.getSearchResults(queryString).cachedIn(viewModelScope)

    }

    fun searchPhotos(query:String){
        currentQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "cats"
    }


}