package com.example.mvvm_imagesearchview.ui.gallery

import androidx.lifecycle.ViewModel
import com.example.mvvm_imagesearchview.data.UnsplashRepositry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


//class GalleryViewModel @ViewModelInject constructor( ViewModelInject class is deprecated
@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repositry: UnsplashRepositry)
    :ViewModel() {
}