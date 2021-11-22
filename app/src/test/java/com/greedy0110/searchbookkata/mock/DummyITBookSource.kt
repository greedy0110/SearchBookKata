package com.greedy0110.searchbookkata.mock

import com.greedy0110.searchbookkata.domain.ITBook
import com.greedy0110.searchbookkata.domain.ITBookSource
import io.reactivex.rxjava3.core.Single

class DummyITBookSource : ITBookSource {
    override fun getByKeyword(keyword: String, page: Int): Single<List<ITBook>> {
        return Single.just(DummyITBooks.page1)
    }
}