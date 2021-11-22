package com.greedy0110.searchbookkata.domain

import io.reactivex.rxjava3.core.Single

class ShowBooksByKeywordUseCase(private val source: ITBookSource) {

    fun execute(keyword: String, page: Int): Single<List<ITBook>> {
        return source.getByKeyword(keyword, page)
    }
}