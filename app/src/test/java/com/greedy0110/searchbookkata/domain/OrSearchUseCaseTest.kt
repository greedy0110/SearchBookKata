package com.greedy0110.searchbookkata.domain

import com.google.common.truth.Truth.assertThat
import com.greedy0110.searchbookkata.mock.DummyITBookSource
import org.junit.Before
import org.junit.Test

class OrSearchUseCaseTest {

    lateinit var itBookSource: ITBookSource

    @Before
    fun setup() {
        itBookSource = DummyITBookSource()
    }

    @Test
    fun testOrSearchUseCase() {
        val keyword1 = "java"
        val keyword2 = "kotlin"
        val showBooksUseCase = ShowBooksByKeywordUseCase(itBookSource)
        val keyword1Result = showBooksUseCase.execute(keyword1, 1).test().values().first()
        val keyword2Result = showBooksUseCase.execute(keyword2, 1).test().values().first()
        val actualResult = keyword1Result.plus(keyword2Result).distinctBy { it.title }

        val orSearchUseCase = OrSearchUseCase(itBookSource)
        val result = orSearchUseCase.execute(keyword1, keyword2, 1).test().values().first()

        //TODO: how can I be sure that the result is okay?
        assertThat(result).hasSize(20)
        assertThat(result).isEqualTo(actualResult)
    }

    @Test
    fun testSearchFail() {
        val orSearchUseCase = OrSearchUseCase(itBookSource)
        val errorKeyword = "error"
        orSearchUseCase.execute(errorKeyword, "java", 1).test()
            .assertError(SearchFailException::class.java)
            .dispose()
    }
}