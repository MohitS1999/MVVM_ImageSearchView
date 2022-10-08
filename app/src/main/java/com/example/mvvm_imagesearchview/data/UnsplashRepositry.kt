package com.example.mvvm_imagesearchview.data

import com.example.mvvm_imagesearchview.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepositry @Inject constructor(private  val unsplashApi: UnsplashApi) {
}
