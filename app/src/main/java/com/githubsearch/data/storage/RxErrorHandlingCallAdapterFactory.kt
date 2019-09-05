package com.githubsearch.data.storage

import com.githubsearch.data.exception.NetworkException
import com.githubsearch.data.model.GithubErrorResponse
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory : CallAdapter.Factory() {

    private val _original by lazy {
        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    companion object {
        fun create() : CallAdapter.Factory = RxErrorHandlingCallAdapterFactory()
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *> {
        val wrapped = _original.get(returnType, annotations, retrofit) as CallAdapter<out Any, *>
        return RxCallAdapterWrapper(wrapped)
    }

    private class RxCallAdapterWrapper<R>(private val wrapped: CallAdapter<R, *>) : CallAdapter<R, Any> {

        private val gson = Gson()

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        override fun adapt(call: Call<R>): Any {
            return when (val result = wrapped.adapt(call)) {
                is Single<*> -> result.onErrorResumeNext { Single.error(asException(it)) }
                is Completable -> result.onErrorResumeNext { Completable.error(asException(it)) }
                else -> result
            }
        }

        private fun asException(throwable: Throwable): Exception {
            if (throwable is HttpException) {
                val response = throwable.response()
                return when (throwable.code()) {
                    403 -> Exception(gson.fromJson(response?.errorBody()?.string(), GithubErrorResponse::class.java).message)
                    else -> Exception("Unexpected Http exception")
                }
            }

            if (throwable is IOException) {
                return NetworkException("Network error", throwable)
            }
            return Exception("Unexpected error")
        }
    }
}