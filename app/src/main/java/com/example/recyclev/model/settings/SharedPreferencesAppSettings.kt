package com.example.recyclev.model.settings

import android.content.Context
import android.content.SharedPreferences

/**
 * Implementation of [AppSettings] based on [SharedPreferences].
 */
class SharedPreferencesAppSettings(
    appContext: Context
) : AppSettings {

    private val sharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    override fun setCurrentToken(token: String?) {
        val editor = sharedPreferences.edit()
        if (token == null)
            editor.remove(PREF_CURRENT_ACCOUNT_TOKEN)
        else
            editor.putString(PREF_CURRENT_ACCOUNT_TOKEN, token)
        editor.apply()
    }

    override fun setCurrentId(id: Long?) {
        val editor = sharedPreferences.edit()
        if (id == null)
            editor.remove(PREF_CURRENT_ACCOUNT_ID)
        else
            editor.putLong(PREF_CURRENT_ACCOUNT_ID, id)
        editor.apply()
    }

    override fun getCurrentId(): Long =
        sharedPreferences.getLong(PREF_CURRENT_ACCOUNT_ID, 0)

    override fun getCurrentToken(): String? =
        sharedPreferences.getString(PREF_CURRENT_ACCOUNT_TOKEN, null)

    companion object {
        private const val PREF_CURRENT_ACCOUNT_TOKEN = "currentToken"
        private const val PREF_CURRENT_ACCOUNT_ID = "currentId"
    }

}