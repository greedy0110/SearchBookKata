package com.greedy0110.searchbookkata.repository

import com.greedy0110.searchbookkata.domain.SearchFailException
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ITBookSourceImpTest {

    @Mock
    lateinit var remoteITBookSource: RemoteITBookSource
    lateinit var itBookSourceImp: ITBookSourceImp

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        itBookSourceImp = ITBookSourceImp(remoteITBookSource)
    }

    @Test
    fun testCatchRepositoryErrorAndRethrowDomainError() {
        // given: remote source throws a repository leve error.
        val keyword = "java"
        val page = 1
        Mockito.`when`(remoteITBookSource.getByKeyword(keyword, page))
            .thenReturn(Single.error(RemoteException("too bad")))

        // when & then
        itBookSourceImp.getByKeyword(keyword, page).test()
            .assertError(SearchFailException::class.java)
            .dispose()
    }
}