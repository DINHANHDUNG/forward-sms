package com.example.forwardsms.telegram

import android.content.SharedPreferences
import android.util.Log
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class TelegramApi @Inject constructor(private val client: OkHttpClient, private val prefs: SharedPreferences) {
    companion object {
        private const val TAG = "TelegramApi"
        private const val BASE = "https://api.telegram.org"
        private const val PREF_BOT_TOKEN = "bot_token"
        private const val PREF_CHAT_ID = "chat_id"
    }

    private fun extractToken(raw: String?): String? {
        if (raw == null) return null
        val s = raw.trim()
        if (s.startsWith("http")) {
            val idx = s.indexOf("/bot")
            if (idx >= 0) {
                val rest = s.substring(idx + 4)
                val end = rest.indexOfAny(charArrayOf('/', '?'))
                return if (end >= 0) rest.substring(0, end) else rest
            }
        }
        return if (s.startsWith("bot")) s.removePrefix("bot") else s
    }

    private fun getToken(): String? = extractToken(prefs.getString(PREF_BOT_TOKEN, null))
    private fun getChatId(): String? = prefs.getString(PREF_CHAT_ID, null)?.trim()

    fun sendMessage(text: String): Boolean {
        val token = getToken() ?: return false
        val chatId = getChatId() ?: return false
        val url = "$BASE/bot$token/sendMessage"
        val form = FormBody.Builder()
            .add("chat_id", chatId)
            .add("text", text)
            .build()
        val request = Request.Builder().url(url).post(form).build()
        return try {
            client.newCall(request).execute().use { response ->
                val ok = response.isSuccessful
                if (!ok) {
                    val bodyStr = try { response.body?.string() } catch (_: Exception) { null }
                    Log.w(TAG, "sendMessage failed: ${response.code} ${response.message} body=$bodyStr")
                }
                ok
            }
        } catch (ex: Exception) {
            Log.e(TAG, "sendMessage exception", ex)
            false
        }
    }

    fun testConnection(): Boolean {
        val token = getToken() ?: return false
        val url = "$BASE/bot$token/getMe"
        val request = Request.Builder().url(url).get().build()
        return try {
            client.newCall(request).execute().use { resp ->
                val ok = resp.isSuccessful
                if (!ok) Log.w(TAG, "testConnection failed: ${resp.code} ${resp.message}")
                ok
            }
        } catch (ex: Exception) {
            Log.e(TAG, "testConnection exception", ex)
            false
        }
    }

    fun getUpdates(offset: Long): String? {
        val token = getToken() ?: return null
        val url = "$BASE/bot$token/getUpdates?offset=$offset&timeout=60"
        val request = Request.Builder().url(url).get().build()
        return try {
            client.newCall(request).execute().use { it.body?.string() }
        } catch (ex: Exception) {
            Log.e(TAG, "getUpdates exception", ex)
            null
        }
    }
}
