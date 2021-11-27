package com.vmakd1916gmail.com.login_logout_register.other

import android.content.Context

private const val PREF_TOKEN = "token"
class TokenPreferences(private val context: Context) {
    fun getStoredToken():String{
        val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(PREF_TOKEN,"")!!
    }

    fun setStoredToken(token:String){
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(PREF_TOKEN, token)
            .apply()
    }
}