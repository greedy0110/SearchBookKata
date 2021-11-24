package com.greedy0110.searchbookkata.domain.usecase

import com.greedy0110.searchbookkata.domain.ITBook
import com.greedy0110.searchbookkata.domain.ITBookSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.Singles
import javax.inject.Inject

class OrSearchUseCase @Inject constructor(private val itBookSource: ITBookSource) {

    fun execute(keyword1: String, keyword2: String, page: Int): Single<List<ITBook>> {
        //TODO: Since the result of keyword1 and keyword2 can be duplicated.
        //  there should be another way of selecting a proper page.
        //  but for simplicity(of course, it's just my ignorant now), I decided to ignore the duplication.
        //  The possible worst case is when keyword1 and keyword2 are the same.
        //  it will show full of duplication code. so, I decided to distinct by its title.
        //  of course it won't affect the other pages though
        return Singles.zip(
            itBookSource.getByKeyword(keyword1, page),
            itBookSource.getByKeyword(keyword2, page),
        ).map { (list1, list2) -> (list1 + list2).distinctBy(ITBook::title) }
    }
}