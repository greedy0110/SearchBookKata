package com.greedy0110.searchbookkata.domain.usecase

import com.greedy0110.searchbookkata.domain.ITBook
import com.greedy0110.searchbookkata.domain.ITBookSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ShowBooksByKeywordUseCase @Inject constructor(private val source: ITBookSource) {

    fun execute(keyword: String, page: Int): Single<List<ITBook>> {
        return source.getByKeyword(keyword, page)
    }
}