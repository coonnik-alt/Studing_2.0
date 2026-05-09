package com.example.recycleview_retrofit

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.Retrofit

class MoviePagingSource : PagingSource<Int , Movie>() {

    //instance repository
    val repository = Retrofit()

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            repository.getMovies

        }
    }
}