package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.api.TmdbApi
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.Request
import okhttp3.HttpUrl
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule {

    companion object {
        const val NAME_BASE_URL = "NAME_BASE_URL"
        const val NAME_API_KEY = "NAME_API_KEY"
    }

    @Provides
    @Named(NAME_BASE_URL)
    fun provideBaseUrlString(): String {
        return BuildConfig.SERVER_BASE_URL
    }

    @Provides
    @Named(NAME_API_KEY)
    fun provideApiKeyString(): String {
        return BuildConfig.API_KEY
    }

    @Provides
    @Singleton
    fun provideMoshiConverter(): Converter.Factory {
        return MoshiConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Provides
    @Singleton
    fun provideLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(@Named(NAME_API_KEY) apiKey: String): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest: Request = chain.request()
                val originalHttpUrl: HttpUrl = originalRequest.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", apiKey)
                    .build()

                val requestBuilder: Request.Builder = originalRequest.newBuilder().url(url)

                val request: Request = requestBuilder.build()
                return chain.proceed(request)
            }
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        converter: Converter.Factory,
        clientBuilder: OkHttpClient.Builder,
        loggingInterceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: Interceptor,
        @Named(NAME_BASE_URL) url: String
    ): Retrofit {
        clientBuilder.addInterceptor(loggingInterceptor)
        clientBuilder.addInterceptor(apiKeyInterceptor)
        return Retrofit.Builder()
            .baseUrl(url)
            .client(clientBuilder.build())
            .addConverterFactory(converter)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }



    @Provides
    @Singleton
    fun provideApiEndpoint(retrofit: Retrofit): TmdbApi {
        return retrofit.create(TmdbApi::class.java)
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}