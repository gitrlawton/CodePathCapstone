package com.codepath.capstone.model

data class LocalResult(
    val position: Int?,
    val title: String?,
    val rating: Float?,  // Note: Float, not Double according to docs
    val reviews_original: String?,
    val reviews: Int?,
    val price: String?,
    val type: String?,
    val address: String?,
    val hours: String?,
    val description: String?,
    val place_id: String?,
    val place_id_search: String?,
    val provider_id: String?,
    val lsig: String?,
    val thumbnail: String?,
    val extensions: List<String>?,
    val gps_coordinates: GpsCoordinates?,
    val links: Map<String, String>?,
)