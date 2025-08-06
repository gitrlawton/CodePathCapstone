package com.codepath.capstone.network

import com.codepath.capstone.model.SerpApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.codepath.capstone.BuildConfig

interface SerpApiService {
    @GET("search.json")
    suspend fun getPlaces(
        @Query("q") query: String,
        @Query("location") location: String,
        @Query("hl") hl: String = "en",
        @Query("gl") gl: String = "us",
        @Query("google_domain") domain: String = "google.com",
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<SerpApiResponse>
}
