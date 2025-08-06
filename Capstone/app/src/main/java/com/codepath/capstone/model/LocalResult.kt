package com.codepath.capstone.model

data class LocalResult(
    val position: Int?,
    val title: String?,
    val place_id: String?,
    val rating: Double?,              // ✅ Needed for rating filter
    val reviews: Int?,
    val price_level: String?,         // ✅ Used in UI
    val price: String?,               // ✅ Used for filtering
    val hours: String?,
    val category: String?,
    val address: String?,
    val thumbnail: String?,
    val gps_coordinates: GpsCoordinates?
)