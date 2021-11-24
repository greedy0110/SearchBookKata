package com.greedy0110.searchbookkata.domain.usecase

import com.greedy0110.searchbookkata.domain.ITBook
import com.greedy0110.searchbookkata.domain.ITBookSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NotSearchUseCase @Inject constructor(private val itBookSource: ITBookSource) {

    fun execute(keyword: String, notKeyword: String, page: Int): Single<List<ITBook>> {
        return itBookSource.getByKeyword(keyword, page)
            .map { list -> list.filterNot { it.title.contains(notKeyword, ignoreCase = true) } }
    }
}