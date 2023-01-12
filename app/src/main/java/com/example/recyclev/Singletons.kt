package com.example.recyclev

import android.content.Context
import com.example.recyclev.model.settings.AppSettings
import com.example.recyclev.model.settings.SharedPreferencesAppSettings
import com.example.recyclev.model.user.UsersRepository
import com.example.recyclev.model.user.UsersSource
import com.example.recyclev.sources.SourceProviderHolder
import com.example.recyclev.sources.SourcesProvider

object Singletons {

    private lateinit var appContext: Context

    private val sourcesProvider: SourcesProvider by lazy {
        SourceProviderHolder.sourcesProvider
    }

    val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(appContext)
    }

    // --- sources

    private val usersSource: UsersSource by lazy {
        sourcesProvider.getUsersSource()
    }
    // --- repositories

    val usersRepository: UsersRepository by lazy {
        UsersRepository(
            usersSource = usersSource,
            appSettings = appSettings
        )
    }
    /**
     * Call this method in all application components that may be created at app startup/restoring
     * (e.g. in onCreate of activities and services)
     */
    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }
}