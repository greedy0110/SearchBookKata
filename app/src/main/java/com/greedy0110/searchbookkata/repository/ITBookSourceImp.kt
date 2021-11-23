package com.greedy0110.searchbookkata.repository

import com.greedy0110.searchbookkata.domain.ITBook
import com.greedy0110.searchbookkata.domain.ITBookSource
import io.reactivex.rxjava3.core.Single

class ITBookSourceImp(private val remoteITBookSource: RemoteITBookSource) : ITBookSource {
    override fun getByKeyword(keyword: String, page: Int): Single<List<ITBook>> {
        return remoteITBookSource.getByKeyword(keyword, page).compose()
    }
}