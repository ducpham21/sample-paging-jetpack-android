package com.mindorks.example.paging3.data.response

import com.squareup.moshi.Json

data class Data(
    @Json(name = "API")
    val api: String,
    @Json(name = "Description")
    val description: String,
    @Json(name = "Auth")
    val auth: String,
    @Json(name = "HTTPS")
    val isHttps: Boolean,
    @Json(name = "Cors")
    val cors: String,
    @Json(name = "Link")
    val link: String,
    @Json(name = "Category")
    val category: String

)

