package com.codepath.capstone.repository

import com.codepath.capstone.model.SerpApiResponse
import com.codepath.capstone.network.RetrofitInstance
import retrofit2.Response
import com.codepath.capstone.BuildConfig


object PlaceRepository {
    suspend fun getPlaces(query: String, location: String): Response<SerpApiResponse> {
        return RetrofitInstance.api.getPlaces(
            query = query,
            location = location,
            hl = "en",               // or your desired language code
            gl = "us",
            domain = "google.com",
            // hl, gl, google_domain use default values
            apiKey = BuildConfig.API_KEY
        )
    }
}
