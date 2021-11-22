package com.greedy0110.searchbookkata.domain

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
            .assertValue(DummyITBooks.page1)
            .dispose()
    }
}