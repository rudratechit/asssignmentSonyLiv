package com.sonyliv.sonylivasssignment

import android.app.Application
import androidx.work.WorkManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.logging.ANDROID
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SonyLivApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SonyLivApplication)
            modules(
                module {
                    single { WorkManager.getInstance(get()) }
                    single { initKtorClient() }
                }
            )
        }
    }

    private fun initKtorClient() = HttpClient(Android) {
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
    }
}
