package com.sebas.platzichallenge.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sebas.platzichallenge.data.datasource.remote.MovieApiService
import com.sebas.platzichallenge.data.mapper.toDomain
import com.sebas.platzichallenge.domain.model.MovieItem
import java.io.IOException
import javax.inject.Inject

class MoviePagingDataSource @Inject constructor(
    private val movieApiService: MovieApiService,
    private val query: String
): PagingSource<Int, MovieItem>() {


    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        return try {
            val page = params.key ?: 1
            val response = if (query.isBlank()) {
                movieApiService.discoverMovies(page)
            } else {
                movieApiService.searchMovies(query, page)
            }
            val movies = response.result
            val nextPage = if (page >= 100 || response.result.isEmpty()) null else page + 1
            val prevPage = if (page == 1) null else page - 1

            LoadResult.Page(
                data = movies.map {
                    it.toDomain()
                },
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}