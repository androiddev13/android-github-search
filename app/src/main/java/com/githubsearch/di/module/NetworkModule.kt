package com.githubsearch.di.module

import android.util.Log
import com.githubsearch.BuildConfig
import com.githubsearch.data.storage.GithubApiService
import com.githubsearch.data.storage.RxErrorHandlingCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(getQueryKeysInterceptor())
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(getQueryKeysInterceptor())
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideGithubApiService(okHttpClient: OkHttpClient): GithubApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
            .build()
            .create(GithubApiService::class.java)
    }

    companion object {

        private const val TAG_NETWORKING_LOG = "Networking"
        private const val KEY_CLIENT_ID = "client_id"
        private const val KEY_CLIENT_SECRET = "client_secret"

        fun getLoggingInterceptor(): HttpLoggingInterceptor {
            val logger = HttpLoggingInterceptor.Logger { message -> Log.d(TAG_NETWORKING_LOG, message) }
            return HttpLoggingInterceptor(logger)
                .apply { level = HttpLoggingInterceptor.Level.BODY }
        }

        /**
         * @return [Interceptor] which adds query with client id and secret keys to each request.
         */
        fun getQueryKeysInterceptor(): Interceptor {
            return Interceptor {
                val url = it.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter(KEY_CLIENT_ID, BuildConfig.CLIENT_ID)
                    .addQueryParameter(KEY_CLIENT_SECRET, BuildConfig.CLIENT_SECRET_KEY)
                    .build()
                it.proceed(it.request().newBuilder().url(url).build())
            }
        }
    }

}