package com.greedy0110.searchbookkata.domain

import com.greedy0110.searchbookkata.domain.usecase.ShowBooksByKeywordUseCase
import com.greedy0110.searchbookkata.mock.DummyITBookSource
import com.greedy0110.searchbookkata.mock.DummyITBooks
import org.junit.Before
import org.junit.Test

class ShowBooksByKeywordUseCaseTest {

    lateinit var itBookSource: ITBookSource

    @Before
    fun setUp() {
        itBookSource = DummyITBookSource()
    }

    @Test
    fun testShowBooksByKeywordUseCase() {
        val showUseCase = ShowBooksByKeywordUseCase(itBookSource)
        val result = showUseCase.execute("java", 1)
        result.test()
            .assertValue(DummyITBooks.javaPage1)
            .dispose()
    }

    @Test
    fun testSearchFail() {
        val showUseCase = ShowBooksByKeywordUseCase(itBookSource)
        val errorKeyword = "error"
        showUseCase.execute(errorKeyword, 1).test()
            .assertError(SearchFailException::class.java)
            .dispose()
    }
}