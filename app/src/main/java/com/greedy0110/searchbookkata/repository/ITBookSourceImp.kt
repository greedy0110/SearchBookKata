package com.greedy0110.searchbookkata.repository

import com.greedy0110.searchbookkata.domain.ITBook
import com.greedy0110.searchbookkata.domain.ITBookSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ITBookSourceImp @Inject constructor(
    private val remoteITBookSource: RemoteITBookSource
) : ITBookSource {
    override fun getByKeyword(keyword: String, page: Int): Single<List<ITBook>> {
        return remoteITBookSource.getByKeyword(keyword, page).compose()
    }
}