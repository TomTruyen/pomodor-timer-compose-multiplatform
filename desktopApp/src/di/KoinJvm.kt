package di

import org.koin.dsl.module
import utils.PlatformProvider
import utils.TrayPositionProvider

val jvmModule = module {
    single<PlatformProvider> { PlatformProvider() }
    
    single {
        TrayPositionProvider(
            platformProvider = get<PlatformProvider>()
        )
    }
}