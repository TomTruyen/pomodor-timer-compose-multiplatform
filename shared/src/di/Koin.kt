package di

import models.PomodoreTimer
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import ui.main.MainViewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
    single<PomodoreTimer> { PomodoreTimer() }
}

val viewModelModule = module {
    viewModelOf(::MainViewModel)
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(appModule, viewModelModule)
}