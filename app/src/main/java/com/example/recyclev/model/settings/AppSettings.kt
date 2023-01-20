package com.example.recyclev.model.settings

interface AppSettings {

    /**
     * Get auth token of the current logged-in user.
     */
    fun getCurrentToken(): String?

    /**
     * Set auth token of the logged-in user.
     */
    fun setCurrentToken(token: String?)

    fun setCurrentId(id: Long?)

    fun getCurrentId(): Long?
}