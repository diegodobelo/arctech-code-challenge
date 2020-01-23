package com.arctouch.codechallenge.datasource

import androidx.paging.DataSource
import com.arctouch.codechallenge.model.Movie
import javax.inject.Inject

class MovieDataSourceFactory @Inject constructor(
    private val movieDataSource: MovieDataSource
) : DataSource.Factory<Int, Movie>() {
    override fun create(): DataSource<Int, Movie> {
        return movieDataSource
    }
}