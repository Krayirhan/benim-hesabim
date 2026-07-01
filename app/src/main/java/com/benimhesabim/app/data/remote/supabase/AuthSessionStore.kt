package com.benimhesabim.app.data.remote.supabase

import android.content.Context
import com.benimhesabim.app.domain.model.AuthUser
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthSessionStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences("auth_session", Context.MODE_PRIVATE)

    fun save(user: AuthUser) {
        prefs.edit()
            .putString(KEY_ID, user.id)
            .putString(KEY_EMAIL, user.email)
            .apply()
    }

    fun read(): AuthUser? {
        val id = prefs.getString(KEY_ID, null) ?: return null
        val email = prefs.getString(KEY_EMAIL, null)
        return AuthUser(id = id, email = email)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    private companion object {
        const val KEY_ID = "id"
        const val KEY_EMAIL = "email"
    }
}
