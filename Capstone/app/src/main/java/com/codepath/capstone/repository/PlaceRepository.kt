package com.codepath.capstone.repository

import com.codepath.capstone.model.SerpApiResponse
import com.codepath.capstone.network.RetrofitInstance
import retrofit2.Response

object PlaceRepository {
    suspend fun getPlaces(query: String, location: String): Response<SerpApiResponse> {
        return RetrofitInstance.api.getPlaces(query, location)
    }
}
