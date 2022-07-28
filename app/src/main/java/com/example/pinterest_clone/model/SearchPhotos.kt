package com.example.pinterest_clone.model

data class SearchPhotos(
    var total: Int? = null,
    var total_pages: Int? = null,
    var results: ArrayList<PhotoHomePage>? = null
)