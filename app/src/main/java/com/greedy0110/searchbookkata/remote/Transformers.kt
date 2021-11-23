package com.greedy0110.searchbookkata.remote

import com.greedy0110.searchbookkata.repository.RemoteException
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleTransformer
import okio.IOException
import retrofit2.HttpException

// wrapping remote layer's specific exceptions into repository layer's exceptions
internal class RemoteSingleTransformer<T : Any> : SingleTransformer<T, T> {
    override fun apply(upstream: Single<T>): Single<T> =
        upstream.onErrorResumeNext { throwable ->
            when (throwable) {
                is IOException -> Single.error(RemoteException(throwable.message))
                is HttpException -> Single.error(RemoteException(throwable.message))
                else -> Single.error(throwable)
            }
        }
}

internal fun <T : Any> Single<T>.compose() = compose(RemoteSingleTransformer())
