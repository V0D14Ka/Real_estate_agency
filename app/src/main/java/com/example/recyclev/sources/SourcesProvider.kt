package com.example.recyclev.sources

import com.example.recyclev.model.user.UsersSource

interface SourcesProvider {
    fun getUsersSource(): UsersSource
}