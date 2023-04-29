package KeyStore

import android.content.Context
import android.content.SharedPreferences

class Preferences {
    private val KEY_TOKEN = "KEY_TOKEN"

    fun setToken(context: Context, token: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    fun getToken(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_TOKEN, null)
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
    }

    fun clearLoggedInUser(context: Context) {
        val editor: SharedPreferences.Editor =
            getSharedPreferences(context).edit()
        editor.remove(KEY_TOKEN)
        editor.commit()
    }
}