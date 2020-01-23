package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.MoviesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    companion object {
        const val PAGE_SIZE = 20
        const val DEFAULT_LANGUAGE = "en-US"
        const val DEFAULT_REGION = ""
    }

    @GET("genre/movie/list")
    fun genres(
        @Query("language") language: String
    ): Observable<GenreResponse>

    @GET("movie/upcoming")
    fun upcomingMovies(
        @Query("language") language: String,
        @Query("page") page: Long,
        @Query("region") region: String
    ): Observable<MoviesResponse>

    @GET("search/movie")
    fun search(
        @Query("query") query: String,
        @Query("page") page: Long
    ): Observable<MoviesResponse>
}
