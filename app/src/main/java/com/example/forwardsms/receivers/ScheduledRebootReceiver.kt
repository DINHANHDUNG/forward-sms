package com.example.forwardsms.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.AlarmManager
import android.app.PendingIntent
import android.os.SystemClock
import okhttp3.OkHttpClient
import com.example.forwardsms.telegram.TelegramApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ScheduledRebootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra("type") ?: "execute"
        val action = intent.getStringExtra("action") ?: "reboot"

        val prefs = context.getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
        val chatId = prefs.getString("chat_id", null)

        val api = TelegramApi(OkHttpClient(), prefs)

        if (type == "warning") {
            // send 1-minute warning and schedule execute
            if (chatId != null) api.sendMessage("⚠️ Scheduled reboot in 1 minute", chatId)
            try {
                val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val execute = Intent(context, ScheduledRebootReceiver::class.java).apply {
                    putExtra("type", "execute")
                    putExtra("action", action)
                }
                val pi = PendingIntent.getBroadcast(context, 0, execute, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                val whenMs = SystemClock.elapsedRealtime() + 60_000L
                am.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, whenMs, pi)
            } catch (_: Exception) {
            }
        } else {
            // execute reboot command asynchronously
            GlobalScope.launch {
                try {
                    if (chatId != null) api.sendMessage("Executing scheduled ${action}", chatId)
                } catch (_: Exception) {}
                try {
                    Runtime.getRuntime().exec(arrayOf("su", "-c", when(action) {
                        "poweroff" -> "poweroff"
                        "recovery" -> "reboot recovery"
                        "bootloader" -> "reboot bootloader"
                        else -> "reboot"
                    }))
                } catch (_: Exception) {
                }
            }
        }
    }
}
