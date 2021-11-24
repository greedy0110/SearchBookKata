package com.greedy0110.searchbookkata.domain.usecase

import com.greedy0110.searchbookkata.domain.ITBookSource
import com.greedy0110.searchbookkata.domain.WrongOperationException
import com.greedy0110.searchbookkata.mock.DummyITBookSource
import com.greedy0110.searchbookkata.mock.DummyITBooks
import org.junit.Before
import org.junit.Test

class SearchBooksUseCaseTest {

    private lateinit var searchBooksUseCase: SearchBooksUseCase
    lateinit var itBookSource: ITBookSource

    @Before
    fun setup() {
        itBookSource = DummyITBookSource()
        val showBooksByKeywordUseCase = ShowBooksByKeywordUseCase(itBookSource)
        val orSearchUseCase = OrSearchUseCase(itBookSource)
        val notSearchUseCase = NotSearchUseCase(itBookSource)
        searchBooksUseCase = SearchBooksUseCase(
            showBooksByKeywordUseCase,
            orSearchUseCase,
            notSearchUseCase
        )
    }

    @Test
    fun testSearchBookUseCaseWithSingleKeyword() {
        val keyword = "java"
        val page = 1
        searchBooksUseCase.execute(keyword, page)
            .test()
            .await()
            .assertComplete()
            .assertValue(DummyITBooks.javaPage1)
    }

    @Test
    fun withOrKeyword() {
        val keyword = "java|kotlin"
        val page = 1
        searchBooksUseCase.execute(keyword, page)
            .test()
            .await()
            .assertComplete()
            .assertValue(DummyITBooks.javaPage1 + DummyITBooks.kotlinPage1)
    }

    @Test
    fun withNotKeyword() {
        val keyword = "java-kotlin"
        val page = 1
        searchBooksUseCase.execute(keyword, page)
            .test()
            .await()
            .assertComplete()
            .assertValue(DummyITBooks.javaPage1.filterNot { it.title.contains("kotlin") })
    }

    @Test
    fun withWrongOperationKeyword() {
        val keyword = "java|kotlin|java"
        val page = 1
        searchBooksUseCase.execute(keyword, page)
            .test()
            .await()
            .assertError(WrongOperationException::class.java)
    }

    @Test
    fun withWrongOperationKeywordMix() {
        val keyword = "java|kotlin-java"
        val page = 1
        searchBooksUseCase.execute(keyword, page)
            .test()
            .await()
            .assertError(WrongOperationException::class.java)
    }

    @Test
    fun withBlankOrEmptyKeyword() {
        val keyword = " "
        val page = 1
        searchBooksUseCase.execute(keyword, page)
            .test()
            .await()
            .assertComplete()
            .assertValue(emptyList())
    }
}