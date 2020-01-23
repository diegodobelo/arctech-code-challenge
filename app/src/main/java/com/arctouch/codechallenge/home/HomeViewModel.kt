package com.arctouch.codechallenge.home

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.adapters.MoviesAdapter
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.datasource.MovieDataSourceFactory

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    moviesDataSourceFactory: MovieDataSourceFactory,
    val adapter: MoviesAdapter
): ViewModel() {
    private val movies: LiveData<PagedList<Movie>>
    private val compositeDisposable = CompositeDisposable()
    private val moviesObserver : Observer<PagedList<Movie>>
    val isLoading = ObservableField<Boolean>(true)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(TmdbApi.PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
        movies = LivePagedListBuilder<Int, Movie>(moviesDataSourceFactory, config).build()
        moviesObserver = Observer { movies ->
            adapter.submitList(movies)
            isLoading.set(false)
        }
        movies.observeForever(moviesObserver)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        movies.removeObserver(moviesObserver)
    }
}