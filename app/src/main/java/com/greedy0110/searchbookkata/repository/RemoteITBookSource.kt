package com.greedy0110.searchbookkata.repository

import com.greedy0110.searchbookkata.domain.ITBook
import io.reactivex.rxjava3.core.Single

interface RemoteITBookSource {
    @Throws(RemoteException::class)
    fun getByKeyword(keyword: String, page: Int): Single<List<ITBook>>
}