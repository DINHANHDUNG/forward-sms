package com.example.forwardsms.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forwardsms.telegram.TelegramApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefs: SharedPreferences,
    private val telegramApi: TelegramApi
) : ViewModel() {

    fun getToken(): String? = prefs.getString("bot_token", "")
    fun getChatId(): String? = prefs.getString("chat_id", "")

    fun save(token: String, chatId: String) {
        prefs.edit().putString("bot_token", token).putString("chat_id", chatId).apply()
    }

    fun getRebootEnabled(): Boolean = prefs.getBoolean("reboot_enabled", false)

    fun setRebootEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("reboot_enabled", enabled).apply()
    }

    fun getRebootScheduleType(): String? = prefs.getString("reboot_schedule_type", "weekly")

    fun getRebootScheduleTime(): String? = prefs.getString("reboot_schedule_time", "03:00")

    fun saveRebootSchedule(type: String, time: String) {
        prefs.edit().putString("reboot_schedule_type", type).putString("reboot_schedule_time", time).apply()
    }

    suspend fun testConnection(): Boolean = withContext(Dispatchers.IO) {
        telegramApi.testConnection()
    }

    suspend fun sendTestMessage(text: String): Boolean = withContext(Dispatchers.IO) {
        telegramApi.sendMessage(text)
    }
}
