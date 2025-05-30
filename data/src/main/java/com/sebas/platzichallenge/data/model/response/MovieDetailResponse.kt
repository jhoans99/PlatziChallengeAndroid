package com.sebas.platzichallenge.data.model.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("adult")
    val adult: String = "",
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("belongs_to_collection")
    val belongCollection: BelongsCollection = BelongsCollection(),
    @SerializedName("genres")
    val genres: List<Genres> = emptyList(),
    @SerializedName("overview")
    val overview: String = "",
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanies> = emptyList(),
    @SerializedName("runtime")
    val runtime: Int = 0,
    @SerializedName("vote_average")
    val voteAverage: Float = 0.0f,
    @SerializedName("tagline")
    val tagLine: String = "",
    @SerializedName("original_title")
    val title: String = "",
    @SerializedName("release_date")
    val releaseDate: String = ""

)

data class BelongsCollection(
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("backdrop_path")
    val backdropPath: String = ""
)

data class Genres(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = ""
)

data class ProductionCompanies(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("logo_path")
    val logoPath: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("origin_country")
    val originCountry: String = ""
)