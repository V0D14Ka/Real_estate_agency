package com.example.recyclev.sources.base

import com.example.recyclev.model.user.UsersSource
import com.example.recyclev.sources.SourcesProvider
import com.example.recyclev.sources.user.RetrofitUsersSource

class RetrofitSourceProvider(
    private val config: RetrofitConfig
) : SourcesProvider {
    override fun getUsersSource(): UsersSource {
        return RetrofitUsersSource(config)
    }
}