package com.codepath.capstone.model

// Top‐level response object
data class SerpApiResponse(
    val local_results: LocalResults?
)

// Wrapper for the “local_results” block
data class LocalResults(
    val more_locations_link: String?,
    val places: List<LocalResult>?
)



// Nested coords object
data class GpsCoordinates(
    val latitude: Double?,
    val longitude: Double?
)
