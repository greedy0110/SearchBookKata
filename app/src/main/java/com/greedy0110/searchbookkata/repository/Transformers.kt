package com.greedy0110.searchbookkata.repository

import com.greedy0110.searchbookkata.domain.SearchFailException
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleTransformer

// wrapping repository layer's exceptions into domain layer's exceptions
internal class RemoteSingleTransformer<T : Any> : SingleTransformer<T, T> {
    override fun apply(upstream: Single<T>): Single<T> =
        upstream.onErrorResumeNext { throwable ->
            when (throwable) {
                is RemoteException -> Single.error(SearchFailException(throwable.message))
                else -> Single.error(throwable)
            }
        }
}

internal fun <T : Any> Single<T>.compose() = compose(RemoteSingleTransformer())
