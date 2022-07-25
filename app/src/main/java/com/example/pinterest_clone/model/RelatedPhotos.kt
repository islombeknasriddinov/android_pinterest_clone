package com.example.pinterest_clone.model

data class RelatedPhotos(
    var total: Int? = null,
    var results: ArrayList<PhotoHomePage>? = null
)