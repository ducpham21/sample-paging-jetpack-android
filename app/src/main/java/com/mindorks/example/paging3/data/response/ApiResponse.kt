package com.mindorks.example.paging3.data.response

import com.squareup.moshi.Json

data class ApiResponse(
    @Json(name = "entries")
    val entries: List<Data>,
    @Json(name = "count")
    val count: Int
)