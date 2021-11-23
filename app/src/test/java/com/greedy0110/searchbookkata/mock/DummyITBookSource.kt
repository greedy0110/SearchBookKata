package com.greedy0110.searchbookkata.mock

import com.greedy0110.searchbookkata.domain.ITBook
import com.greedy0110.searchbookkata.domain.ITBookSource
import io.reactivex.rxjava3.core.Single

class DummyITBookSource : ITBookSource {
    override fun getByKeyword(keyword: String, page: Int): Single<List<ITBook>> {
        val list = when (keyword) {
            "java" -> DummyITBooks.javaPage1
            "kotlin" -> DummyITBooks.kotlinPage1
            "halfJava" -> DummyITBooks.halfJavaPage1
            else -> throw UnsupportedOperationException("unsupported keyword in this test.")
        }
        return Single.just(list)
    }
}