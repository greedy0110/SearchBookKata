package com.greedy0110.searchbookkata.remote.di

import com.greedy0110.searchbookkata.remote.RestITBookSource
import com.greedy0110.searchbookkata.repository.RemoteITBookSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BookSourceModule {

    @Binds
    abstract fun bindsRemoteITBookSource(
        restITBookSource: RestITBookSource
    ): RemoteITBookSource
}