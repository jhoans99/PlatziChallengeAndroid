package com.sebas.platzichallenge.data.model.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: String,
    @SerializedName("total_pages")
    val totalPages: String,
    @SerializedName("total_results")
    val totalResult: String,
    @SerializedName("results")
    val result: List<MovieItemResponse>
)
