package com.example.mvvm_imagesearchview

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashPhoto(
    val id: String,
    val description: String?,
    val urls: UnsplashPhotoUrls,
    val user: UnsplashPhotoUser
) : Parcelable {

    @Parcelize
    data class UnsplashPhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
    ) : Parcelable

    @Parcelize
    data class UnsplashPhotoUser(
        val name: String,
        val username: String
    ) : Parcelable {
        val attibutionUrl get() = "https://unsplash.com/$username?utm_source=MVVM_ImageSearchView&utm_medium=referral"
    }

}