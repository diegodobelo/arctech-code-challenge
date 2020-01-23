package com.arctouch.codechallenge.datasource

import androidx.paging.DataSource
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.Movie
import io.reactivex.disposables.CompositeDisposable

class SearchMovieDataSourceFactory(
    private val api: TmdbApi,
    private val compositeDisposable: CompositeDisposable,
    private val searchQuery: String
) : DataSource.Factory<Int, Movie>() {
    override fun create(): DataSource<Int, Movie> {
        return SearchMovieDataSource(api, compositeDisposable, searchQuery)
    }
}