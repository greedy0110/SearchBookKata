package com.greedy0110.searchbookkata.repository.di

import com.greedy0110.searchbookkata.domain.ITBookSource
import com.greedy0110.searchbookkata.repository.ITBookSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ITBookSourceModule {

    @Binds
    abstract fun bindsITBookSource(
        itBookSourceImp: ITBookSourceImp
    ): ITBookSource
}