package com.greedy0110.searchbookkata.domain

import io.reactivex.rxjava3.core.Single

interface ITBookSource {
    @Throws(SearchFailException::class)
    fun getByKeyword(keyword: String, page: Int): Single<List<ITBook>>
}