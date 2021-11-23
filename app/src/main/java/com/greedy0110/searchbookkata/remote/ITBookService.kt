package com.greedy0110.searchbookkata.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ITBookService {

    @GET("/1.0/search/{keyword}/{page}")
    fun searchBooks(
        @Path("keyword") keyword: String,
        @Path("page") page: Int
    ): Single<SearchBooksResponse>
}