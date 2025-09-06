package com.wsayan.secureapikey.di

import com.wsayan.secureapikey.MainActivityViewModel
import com.wsayan.secureapikey.data.dataModule
import com.wsayan.secureapikey.domain.domainModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {
    includes(
        dataModule(),
        domainModule(),

        module {
            viewModel { MainActivityViewModel(get()) }
        }
    )
}