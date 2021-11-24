package com.greedy0110.searchbookkata.domain.usecase

import com.greedy0110.searchbookkata.domain.ITBook
import com.greedy0110.searchbookkata.domain.WrongOperationException
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchBooksUseCase @Inject constructor(
    private val showBooksByKeywordUseCase: ShowBooksByKeywordUseCase,
    private val orSearchUseCase: OrSearchUseCase,
    private val notSearchUseCase: NotSearchUseCase
) {

    fun execute(keyword: String, page: Int): Single<List<ITBook>> {
        val orKeywords = keyword.split(orOperator)
        val notKeywords = keyword.split(notOperator)
        val orSize = orKeywords.size
        val notSize = notKeywords.size
        return when {
            keyword.isBlank() -> Single.just(emptyList())
            orSize >= 2 && notSize >= 2 -> Single.error(WrongOperationException(keyword))
            orSize >= 3 || notSize >= 3 -> Single.error(WrongOperationException(keyword))
            orSize == 2 -> orSearchUseCase.execute(orKeywords[0], orKeywords[1], page)
            notSize == 2 -> notSearchUseCase.execute(notKeywords[0], notKeywords[1], page)
            else -> showBooksByKeywordUseCase.execute(keyword, page)
        }
    }

    companion object {
        private const val orOperator = "|"
        private const val notOperator = "-"
    }
}