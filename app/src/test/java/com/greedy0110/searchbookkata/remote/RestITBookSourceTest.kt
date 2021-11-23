package com.greedy0110.searchbookkata.remote

import com.google.common.truth.Truth.assertThat
import com.greedy0110.searchbookkata.domain.ITBook
import com.greedy0110.searchbookkata.mock.DummyITBooks
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import javax.inject.Inject
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.HttpURLConnection

@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
@HiltAndroidTest
class RestITBookSourceTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var moshi: Moshi

    @Inject
    lateinit var itBookService: ITBookService

    lateinit var restITBookSource: RestITBookSource

    private val mockServer: MockWebServer = MockWebServer()

    //TODO: 1. provide mock web server to retrofit, to do this, we have to inject a new retrofit object.
    //TODO: 2. design dispatchers for testing. (when it works and it throws errors)

    @Before
    fun setup() {
        hiltAndroidRule.inject()
        restITBookSource = RestITBookSource(itBookService)
        mockServer.start()
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun testSearchBooks() {
        val properDispatcher = SearchBooksDispatcher(
            moshi = moshi,
            getITBooks = { DummyITBooks.javaPage1.subList(0, 3) }
        )
        mockServer.dispatcher = properDispatcher

        val keyword = "java"
        val page = 1
        val result = restITBookSource.getByKeyword(keyword, page).test().values().first()

        assertThat(result).hasSize(3)
        assertThat(result).isEqualTo(DummyITBooks.javaPage1.subList(0, 3))
    }

    private class SearchBooksDispatcher(
        private val moshi: Moshi,
        private val getITBooks: () -> List<ITBook>
    ) : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when {
                request.path?.startsWith("/1.0/search/{keyword}/{page}") == true -> {

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
                }
                else -> throw UnsupportedOperationException("the request is not allowed on this dispatcher.")
            }
        }
    }
}