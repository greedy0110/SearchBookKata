package com.greedy0110.searchbookkata.remote

import com.greedy0110.searchbookkata.domain.ITBook
import com.greedy0110.searchbookkata.mock.DummyITBooks
import com.greedy0110.searchbookkata.repository.RemoteException
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.create
import java.net.HttpURLConnection

class RestITBookSourceTest {

    lateinit var restITBookSource: RestITBookSource

    lateinit var itBookService: ITBookService
    lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        itBookService = getRetrofit(mockWebServer).create()
        restITBookSource = RestITBookSource(itBookService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testSearchBooks() {
        mockWebServer.dispatcher = SearchBooksDispatcher(
            getITBooks = { DummyITBooks.javaPage1.subList(0, 3) }
        )

        val keyword = "java"
        val page = 1
        restITBookSource.getByKeyword(keyword, page)
            .test()
            .await()
            .assertComplete()
            .assertValue(DummyITBooks.javaPage1.subList(0, 3))
    }

    @Test
    fun catchRetrofitErrorAndRethrowRepositoryError() {
        mockWebServer.dispatcher = SearchBooksDispatcher(
            getITBooks = { throw Exception() }
        )

        val keyword = "java"
        val page = 1
        restITBookSource.getByKeyword(keyword, page)
            .test()
            .await()
            .assertError(RemoteException::class.java)
    }

    private class SearchBooksDispatcher(
        private val getITBooks: () -> List<ITBook>
    ) : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when {
                request.path?.startsWith("/1.0/search/") == true -> {
                    try {
                        val books = getITBooks()
                            .map { it.toSearchBooksResponseBook() }
                        val responseObject = SearchBooksResponse(
                            error = 0,
                            total = 100,
                            page = 1,
                            books = books
                        )
                        val adapter = moshi.adapter(SearchBooksResponse::class.java)
                        val rawResponse = adapter.toJson(responseObject)
                        MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(rawResponse)
                    } catch (throwable: Throwable) {
                        MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                    }
                }
                else -> throw UnsupportedOperationException("the request is not allowed on this dispatcher.")
            }
        }
    }
}