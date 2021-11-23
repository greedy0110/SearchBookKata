package com.greedy0110.searchbookkata.domain

import io.reactivex.rxjava3.core.Single

class NotSearchUseCase(private val itBookSource: ITBookSource) {

    fun execute(keyword: String, notKeyword: String, page: Int): Single<List<ITBook>> {
        return itBookSource.getByKeyword(keyword, page)
            .map { list -> list.filterNot { it.title.contains(notKeyword, ignoreCase = true) } }
    }
}