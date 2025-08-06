package com.codepath.capstone.model

// Top‐level response object
data class SerpApiResponse(
    val local_results: List<LocalResult>?  // ✅ Direct list
)



// Nested coords object
data class GpsCoordinates(
    val latitude: Double?,
    val longitude: Double?
)
