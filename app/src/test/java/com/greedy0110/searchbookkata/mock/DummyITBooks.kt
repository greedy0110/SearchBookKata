package com.greedy0110.searchbookkata.mock

import com.greedy0110.searchbookkata.domain.ITBook
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.buffer
import okio.source
import java.io.File

object DummyITBooks {
    private val moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val javaPage1: List<ITBook> by lazy {
        getBooksFromFileName("src/test/java/com/greedy0110/searchbookkata/mock/java_books_result.json")
    }

    val kotlinPage1: List<ITBook> by lazy {
        getBooksFromFileName("src/test/java/com/greedy0110/searchbookkata/mock/kotlin_books_result.json")
    }

    private fun getBooksFromFileName(filename: String): List<ITBook> {
        val rawBuffer = File(filename).source().buffer()
        val itBooksJsonAdapter = moshi.adapter(JsonWrapObject::class.java)
        return itBooksJsonAdapter.fromJson(rawBuffer)
            ?.books
            ?.map { it.toITBook() }
            .orEmpty()
    }

    private data class JsonWrapObject(
        val error: String,
        val total: String,
        val page: String,
        val books: List<ITBookWrap>
    )

    private data class ITBookWrap(
        val title: String,
        val subtitle: String,
        val isbn13: String,
        val price: String,
        val image: String,
        val url: String
    )

    private fun ITBookWrap.toITBook(): ITBook = ITBook(title, subtitle, isbn13, price, image, url)
}