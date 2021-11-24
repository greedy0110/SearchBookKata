package com.greedy0110.searchbookkata.domain

import com.google.common.truth.Truth.assertThat
import com.greedy0110.searchbookkata.domain.usecase.NotSearchUseCase
import com.greedy0110.searchbookkata.domain.usecase.ShowBooksByKeywordUseCase
import com.greedy0110.searchbookkata.mock.DummyITBookSource
import org.junit.Before
import org.junit.Test

class NotSearchUseCaseTest {

    lateinit var itBookSource: ITBookSource

    @Before
    fun setup() {
        itBookSource = DummyITBookSource()
    }

    @Test
    fun testNotSearchUseCaseWithNoDuplication() {
        val keyword1 = "java"
        val keyword2 = "kotlin"
        val showBooksUseCase = ShowBooksByKeywordUseCase(itBookSource)
        val keyword1Result = showBooksUseCase.execute(keyword1, 1).test().values().first()

        val notUseCase = NotSearchUseCase(itBookSource)
        val result = notUseCase.execute(keyword1, keyword2, 1).test().values().first()

        assertThat(result).hasSize(10)
        assertThat(result).isEqualTo(keyword1Result)
    }

    @Test
    fun testNotSearchUseCaseWithFullOfDuplication() {
        val keyword1 = "java"
        val keyword2 = "java"

        val notUseCase = NotSearchUseCase(itBookSource)
        val result = notUseCase.execute(keyword1, keyword2, 1).test().values().first()

        assertThat(result).isEmpty()
    }

    @Test
    fun testNotSearchUseCaseWithHalfDuplication() {
        val keyword1 = "halfJava"
        val keyword2 = "java"

        val notUseCase = NotSearchUseCase(itBookSource)
        val result = notUseCase.execute(keyword1, keyword2, 1).test().values().first()

        assertThat(result).hasSize(4)
    }

    @Test
    fun testSearchFail() {
        val errorKeyword = "error"
        val keyword2 = "java"
        val notUseCase = NotSearchUseCase(itBookSource)
        notUseCase.execute(errorKeyword, keyword2, 1).test()
            .assertError(SearchFailException::class.java)
            .dispose()
    }
}