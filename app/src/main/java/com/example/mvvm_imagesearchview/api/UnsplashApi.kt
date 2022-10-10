package com.example.mvvm_imagesearchview.api


import com.example.mvvm_imagesearchview.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface UnsplashApi {


    companion object {
        const val BASE_URL = "https://api.unsplash.com/"

        const val CLIENT_ID = BuildConfig.unsplash_access_key
    }
    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int

    ): UnsplashRespose

}