package com.arctouch.codechallenge.search

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.adapters.MoviesAdapter
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.datasource.SearchMovieDataSourceFactory

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val api: TmdbApi,
    private val compositeDisposable: CompositeDisposable,
    val adapter: MoviesAdapter
): ViewModel() {
    private var movies: LiveData<PagedList<Movie>>? = null
    private var moviesObserver : Observer<PagedList<Movie>>? = null
    val isLoading = ObservableField<Boolean>(true)
    private var hasPerformedSearch = false

    fun performSearch(searchQuery: String) {
        if (hasPerformedSearch) return
        val config = PagedList.Config.Builder()
            .setPageSize(TmdbApi.PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
        val searchMoviesDataSourceFactory = SearchMovieDataSourceFactory(api, compositeDisposable, searchQuery)
        movies = LivePagedListBuilder<Int, Movie>(searchMoviesDataSourceFactory, config).build()
        val moviesObserver : Observer<PagedList<Movie>> = Observer { movies ->
            adapter.submitList(movies)
            isLoading.set(false)
            hasPerformedSearch = true
        }
        movies?.observeForever(moviesObserver)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        moviesObserver?.let {
            movies?.removeObserver(it)
        }
    }
}