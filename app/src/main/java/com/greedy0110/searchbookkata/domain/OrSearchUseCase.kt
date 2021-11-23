package com.greedy0110.searchbookkata.domain

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.Singles

class OrSearchUseCase(private val itBookSource: ITBookSource) {

    fun execute(keyword1: String, keyword2: String): Single<List<ITBook>> {
        //TODO: what should a page be?
        return Singles.zip(
            itBookSource.getByKeyword(keyword1, 1),
            itBookSource.getByKeyword(keyword2, 1),
        ).map { (list1, list2) -> (list1 + list2).distinctBy(ITBook::title) }
    }
}