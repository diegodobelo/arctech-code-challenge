package com.arctouch.codechallenge.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.MoviesResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class SearchMovieDataSource constructor(
    private val api: TmdbApi,
    private val compositeDisposable: CompositeDisposable,
    private val searchQuery: String
) : PageKeyedDataSource<Int, Movie>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        val disposable = Observable.zip(
            api.genres(TmdbApi.DEFAULT_LANGUAGE),
            api.search(searchQuery, 1),
            BiFunction { genreResponse: GenreResponse, movieResponse: MoviesResponse -> genreResponse to movieResponse }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Cache.cacheGenres(it.first.genres)
                    val moviesWithGenres = it.second.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { genre -> movie.genreIds?.contains(genre.id) == true })
                    }
                    callback.onResult(moviesWithGenres, it.second.page, it.second.page + 1)
                },
                {
                    Log.e(SearchMovieDataSource::class.toString(), it?.message ?: "")
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val disposable = api.search(searchQuery, params.key.toLong())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val moviesWithGenres = it.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                    callback.onResult(moviesWithGenres, it.page + 1)
                },
                {
                    Log.e(SearchMovieDataSource::class.toString(), it?.message ?: "")
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }
}