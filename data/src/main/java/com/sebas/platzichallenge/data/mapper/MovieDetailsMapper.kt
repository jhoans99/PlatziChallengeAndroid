package com.sebas.platzichallenge.data.mapper

import com.sebas.platzichallenge.core.util.formatDate
import com.sebas.platzichallenge.data.model.response.MovieDetailResponse
import com.sebas.platzichallenge.domain.model.Companies
import com.sebas.platzichallenge.domain.model.DetailMovie
import com.sebas.platzichallenge.domain.model.Genre

fun MovieDetailResponse.toDomain() = DetailMovie(
    backdropPath = "https://image.tmdb.org/t/p/w500/${backdropPath}",
    title = title,
    tagline = tagLine,
    overview = overview,
    genres = genres.map { Genre(
        it.id,it.name
    ) },
    releaseDate = releaseDate.formatDate(),
    runtime = runtime,
    voteAverage = voteAverage,
    companies = productionCompanies.map {
        Companies(
            name = it.name,
            image = "https://image.tmdb.org/t/p/w500/${it.logoPath}"
        )
    }
)