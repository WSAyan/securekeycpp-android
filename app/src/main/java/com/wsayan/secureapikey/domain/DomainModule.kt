package com.wsayan.secureapikey.domain

import com.wsayan.secureapikey.domain.usecase.GetAnimeListUseCase
import org.koin.dsl.module

fun domainModule() = module {
    factory { GetAnimeListUseCase(get()) }
}