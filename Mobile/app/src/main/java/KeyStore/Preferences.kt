package KeyStore

import android.content.Context
import android.content.SharedPreferences

class Preferences {
    private val KEY_TOKEN = "KEY_TOKEN"
    private val KEY_JURUSAN = "JURUSAN"
    private val KEY_PRODI = "PRODI"

    fun setToken(context: Context, token: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    fun getToken(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_TOKEN, null)
    }

    fun setJurusan(context: Context, jurusan: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_JURUSAN, jurusan)
        editor.apply()
    }

    fun getJurusan(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_JURUSAN, null)
    }

    fun setProdi(context: Context, prodi: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_PRODI, prodi)
        editor.apply()
    }

    fun getProdi(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_PRODI, null)
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

    fun deleteFilter(context: Context){
        val editor = getSharedPreferences(context).edit()
        editor.remove(KEY_JURUSAN)
        editor.remove(KEY_PRODI)
        editor.apply()
    }
}