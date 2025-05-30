package com.sebas.platzichallenge.data.model.response

import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("page")
    val page: String = "",
    @SerializedName("results")
    val results: List<MovieReviewItem> = emptyList()
)

data class MovieReviewItem(
    @SerializedName("author")
    val author: String,
    @SerializedName("author_details")
    val authorDetails: AuthorDetails,
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val createDte: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("updated_at")
    val updateDate: String,
    @SerializedName("url")
    val url: String
)

data class AuthorDetails(
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val userName: String,
    @SerializedName("avatar_path")
    val avatarPath: String?,
    @SerializedName("rating")
    val rating: Float?
)
