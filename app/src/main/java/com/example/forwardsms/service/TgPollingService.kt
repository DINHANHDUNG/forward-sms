package com.example.forwardsms.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.content.IntentFilter
import android.os.BatteryManager
import android.content.BroadcastReceiver
import android.telephony.TelephonyManager
import android.util.Log
import java.util.Locale
import android.app.AlarmManager
import android.app.PendingIntent
import java.util.Calendar
import kotlinx.coroutines.delay
import com.example.forwardsms.data.dao.PendingMessageDao
import com.example.forwardsms.repository.MessageRepository
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import com.example.forwardsms.data.dao.ForwardedSmsDao
import com.example.forwardsms.data.dao.LogDao
import com.example.forwardsms.telegram.TelegramApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject
import android.net.ConnectivityManager
import android.net.Network
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.forwardsms.work.QueueWorker

@AndroidEntryPoint
class TgPollingService : Service() {
    @Inject
    lateinit var telegramApi: TelegramApi

    @Inject
    lateinit var forwardedDao: ForwardedSmsDao

    @Inject
    lateinit var logDao: LogDao

    @Inject
    lateinit var pendingDao: PendingMessageDao

    @Inject
    lateinit var repo: MessageRepository

    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private var offset: Long = 0

    override fun onCreate() {
        super.onCreate()
        startForeground(1001, createNotification())

        // resume offset from preferences
        val prefs = getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
        offset = prefs.getLong("tg_offset", 0L)

        scope.launch {
            while (isActive) {
                try {
                    val body = telegramApi.getUpdates(offset)
                    if (body != null) {
                        val jo = JSONObject(body)
                        val results = jo.optJSONArray("result")
                        if (results != null) {
                            for (i in 0 until results.length()) {
                                val upd = results.getJSONObject(i)
                                val updateId = upd.optLong("update_id", -1)
                                if (updateId >= 0) offset = updateId + 1
                                // store offset
                                getSharedPreferences("secure_prefs", Context.MODE_PRIVATE).edit().putLong("tg_offset", offset).apply()

                                val message = upd.optJSONObject("message") ?: continue
                                val chat = message.optJSONObject("chat") ?: continue
                                val text = message.optString("text") ?: continue

                                if (text.startsWith("/reply")) {
                                    // format: /reply <id|phone> <message>
                                    val parts = text.split(" ", limit = 3)
                                    if (parts.size >= 3) {
                                        val target = parts[1]
                                        val replyText = parts[2]

                                        var phone: String? = null
                                        // send a brief "gateway online" status on service start
                                        scope.launch {
                                            try {
                                                val bm = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
                                                val battery = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
                                                val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                                                val simState = tm.simState
                                                val simActive = when (simState) {
                                                    TelephonyManager.SIM_STATE_READY -> "Active"
                                                    else -> "Inactive"
                                                }
                                                val cm0 = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                                                val network = if (cm0.activeNetwork != null) "Connected" else "Disconnected"

                                                val pendingCount = try { pendingDao.getPending().size } catch (_: Exception) { 0 }

                                                val status = StringBuilder()
                                                    .append("❤️ Gateway Online\n")
                                                    .append("Battery: ").append(battery).append("%\n")
                                                    .append("Temperature: -\n")
                                                    .append("Network: ").append(network).append("\n")
                                                    .append("SIM: ").append(simActive).append("\n")
                                                    .append("Queue: ").append(pendingCount)
                                                    .toString()

                                                telegramApi.sendMessage(status)
                                            } catch (_: Exception) {
                                            }

                                            // main polling loop
                                            while (isActive) {
                                        val byRef = forwardedDao.getByMessageRef(target)
                                        if (byRef != null) phone = byRef.sender
                                        else {
                                            // try numeric phone
                                            if (target.matches(Regex("[+0-9\\- ]+"))) {
                                                phone = target
                                            }
                                        }

                                        if (phone != null) {
                                            try {
                                                SmsManager.getDefault().sendTextMessage(phone, null, replyText, null, null)
                                                                // handle callback_query (inline button presses)
                                                                val callback = upd.optJSONObject("callback_query")
                                                                if (callback != null) {
                                                                    val data = callback.optString("data") ?: ""
                                                                    val cbId = callback.optString("id")
                                                                    val from = callback.optJSONObject("from")
                                                                    val fromId = from?.optLong("id", -1L) ?: -1L
                                                                    try {
                                                                        if (!isAuthorized(fromId)) {
                                                                            telegramApi.answerCallbackQuery(cbId, "Access denied")
                                                                        } else {
                                                                            if (data.startsWith("confirm:")) {
                                                                                val action = data.substringAfter(":")
                                                                                telegramApi.answerCallbackQuery(cbId, "Confirmed")
                                                                                performRootAction(action, fromId)
                                                                            } else if (data.startsWith("cancel:")) {
                                                                                telegramApi.answerCallbackQuery(cbId, "Cancelled")
                                                                                telegramApi.sendMessage("Cancelled", fromId.toString())
                                                                            }
                                                                        }
                                                                    } catch (ex: Exception) {
                                                                    }
                                                                    continue
                                                                }

                                                                val message = upd.optJSONObject("message") ?: continue
                                                                val chat = message.optJSONObject("chat") ?: continue
                                                                val text = message.optString("text") ?: continue
                                                                val chatId = chat.optLong("id", -1L)

                                                                if (!isAuthorized(chatId)) {
                                                                    // ignore unauthorized
                                                                    continue
                                                                }

                                                                if (text.startsWith("/reply")) {
                                                                    // format: /reply <id|phone> <message>
                                                                    val parts = text.split(" ", limit = 3)
                                                                    if (parts.size >= 3) {
                                                                        val target = parts[1]
                                                                        val replyText = parts[2]

                                                                        var phone: String? = null
                                                                        // try lookup by messageRef
                                                                        val byRef = forwardedDao.getByMessageRef(target)
                                                                        if (byRef != null) phone = byRef.sender
                                                                        else {
                                                                            // try numeric phone
                                                                            if (target.matches(Regex("[+0-9\\- ]+"))) {
                                                                                phone = target
                                                                            }
                                                                        }

                                                                        if (phone != null) {
                                                                            try {
                                                                                SmsManager.getDefault().sendTextMessage(phone, null, replyText, null, null)
                                                                                telegramApi.sendMessage("✅ SMS Sent\nTo:${phone}\nMessage:${replyText}", chatId.toString())
                                                                                // store log (no content in logs)
                                                                                logDao.insert(com.example.forwardsms.data.entities.LogEntry(type = "OUTGOING_SMS", message = "To:$phone"))
                                                                            } catch (ex: Exception) {
                                                                                telegramApi.sendMessage("Failed to send SMS to ${phone}", chatId.toString())
                                                                            }
                                                                        } else {
                                                                            telegramApi.sendMessage("Reply failed: unknown target $target", chatId.toString())
                                                                        }
                                                                    } else {
                                                                        telegramApi.sendMessage("Usage: /reply <message_ref_or_phone> <text>", chatId.toString())
                                                                    }
                                                                } else if (text.startsWith("/sms")) {
                                                                    // format: /sms <phone> <message>
                                                                    val parts = text.split(" ", limit = 3)
                                                                    if (parts.size >= 3) {
                                                                        val phone = parts[1]
                                                                        val body = parts[2]
                                                                        // rate limiting per day
                                                                        val prefs2 = getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
                                                                        val dayKey = "sms_count_" + java.text.SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(java.util.Date())
                                                                        val sentToday = prefs2.getInt(dayKey, 0)
                                                                        if (sentToday >= 50) {
                                                                            telegramApi.sendMessage("SMS rate limit reached (50/day)", chatId.toString())
                                                                        } else {
                                                                            try {
                                                                                SmsManager.getDefault().sendTextMessage(phone, null, body, null, null)
                                                                                prefs2.edit().putInt(dayKey, sentToday + 1).apply()
                                                                                telegramApi.sendMessage("✅ SMS Sent\nTo:${phone}\nMessage:${body}\nTime:${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(java.util.Date())}", chatId.toString())
                                                                                logDao.insert(com.example.forwardsms.data.entities.LogEntry(type = "OUTGOING_SMS", message = "To:$phone"))
                                                                            } catch (ex: Exception) {
                                                                                telegramApi.sendMessage("Failed to send SMS to ${phone}", chatId.toString())
                                                                            }
                                                                        }
                                                                    } else {
                                                                        telegramApi.sendMessage("Usage: /sms <phone> <message>", chatId.toString())
                                                                    }
                                                                } else if (text.startsWith("/reboot") || text.startsWith("/poweroff") || text.startsWith("/recovery") || text.startsWith("/bootloader")) {
                                                                    // ask for confirmation via inline keyboard
                                                                    val action = when {
                                                                        text.startsWith("/reboot") -> "reboot"
                                                                        text.startsWith("/poweroff") -> "poweroff"
                                                                        text.startsWith("/recovery") -> "recovery"
                                                                        else -> "bootloader"
                                                                    }
                                                                    val replyMarkup = "{\"inline_keyboard\":[[{\"text\":\"✅ Confirm\",\"callback_data\":\"confirm:${action}\"},{\"text\":\"❌ Cancel\",\"callback_data\":\"cancel:${action}\"}]]}"
                                                                    telegramApi.sendMessage("⚠️ Confirm Device ${action.replaceFirstChar { it.uppercaseChar() }}?", chatId.toString(), replyMarkup)
                                                                } else if (text.startsWith("/set_reboot_schedule")) {
                                                                    // format: /set_reboot_schedule <daily|weekly|monthly> HH:mm
                                                                    val parts = text.split(" ", limit = 3)
                                                                    if (parts.size >= 3) {
                                                                        val period = parts[1].lowercase(Locale.getDefault())
                                                                        val time = parts[2]
                                                                        val prefs2 = getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
                                                                        prefs2.edit().putString("reboot_schedule_type", period).putString("reboot_schedule_time", time).apply()
                                                                        // compute next occurrence and schedule alarms
                                                                        try {
                                                                            val timeParts = time.split(":")
                                                                            val hour = timeParts[0].toInt()
                                                                            val minute = timeParts[1].toInt()
                                                                            val next = java.util.Calendar.getInstance()
                                                                            next.set(java.util.Calendar.HOUR_OF_DAY, hour)
                                                                            next.set(java.util.Calendar.MINUTE, minute)
                                                                            next.set(java.util.Calendar.SECOND, 0)
                                                                            next.set(java.util.Calendar.MILLISECOND, 0)
                                                                            if (next.timeInMillis <= System.currentTimeMillis()) {
                                                                                when (period) {
                                                                                    "daily" -> next.add(java.util.Calendar.DAY_OF_YEAR, 1)
                                                                                    "weekly" -> next.add(java.util.Calendar.WEEK_OF_YEAR, 1)
                                                                                    "monthly" -> next.add(java.util.Calendar.MONTH, 1)
                                                                                    else -> next.add(java.util.Calendar.WEEK_OF_YEAR, 1)
                                                                                }
                                                                            }

                                                                            val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                                                                            val warnIntent = Intent(applicationContext, com.example.forwardsms.receivers.ScheduledRebootReceiver::class.java).apply {
                                                                                putExtra("type", "warning")
                                                                                putExtra("action", "reboot")
                                                                            }
                                                                            val warnPi = PendingIntent.getBroadcast(applicationContext, 1001, warnIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                                                                            val execIntent = Intent(applicationContext, com.example.forwardsms.receivers.ScheduledRebootReceiver::class.java).apply {
                                                                                putExtra("type", "execute")
                                                                                putExtra("action", "reboot")
                                                                            }
                                                                            val execPi = PendingIntent.getBroadcast(applicationContext, 1002, execIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                                                                            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next.timeInMillis - 60_000L, warnPi)
                                                                            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next.timeInMillis, execPi)

                                                                            telegramApi.sendMessage("Scheduled reboot set: $period at $time", chatId.toString())
                                                                        } catch (ex: Exception) {
                                                                            telegramApi.sendMessage("Failed to schedule reboot: ${ex.message}", chatId.toString())
                                                                        }
                                                                    } else {
                                                                        telegramApi.sendMessage("Usage: /set_reboot_schedule <daily|weekly|monthly> HH:mm", chatId.toString())
                                                                    }
                                                                } else if (text.startsWith("/enable_reboot")) {
                                                                    getSharedPreferences("secure_prefs", Context.MODE_PRIVATE).edit().putBoolean("reboot_enabled", true).apply()
                                                                    telegramApi.sendMessage("Scheduled reboot enabled", chatId.toString())
                                                                } else if (text.startsWith("/disable_reboot")) {
                                                                    getSharedPreferences("secure_prefs", Context.MODE_PRIVATE).edit().putBoolean("reboot_enabled", false).apply()
                                                                    telegramApi.sendMessage("Scheduled reboot disabled", chatId.toString())
                                                                }
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun isAuthorized(id: Long): Boolean {
        if (id <= 0) return false
        val prefs = getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
        val allowed = prefs.getString("chat_id", "") ?: ""
        if (allowed.isBlank()) return false
        val parts = allowed.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        return parts.contains(id.toString())
    }

    private suspend fun performRootAction(action: String, fromId: Long) {
        try {
            val chatId = fromId.toString()
            telegramApi.sendMessage("Preparing ${action} — saving queue, flushing DB, stopping services...", chatId)

            // trigger queue flush
            val work = OneTimeWorkRequestBuilder<QueueWorker>().build()
            WorkManager.getInstance(applicationContext).enqueue(work)

            // wait for pending to drain (up to 30s)
            var waited = 0
            while (waited < 30) {
                val pending = try { pendingDao.getPending().size } catch (_: Exception) { 0 }
                if (pending == 0) break
                delay(2000)
                waited += 2
            }

            telegramApi.sendMessage("Executing ${action}", chatId)
            logDao.insert(com.example.forwardsms.data.entities.LogEntry(type = "REBOOT_ACTION", message = "Action:$action"))

            // stop service gracefully
            try {
                stopForeground(true)
                stopSelf()
            } catch (_: Exception) {}

            val cmd = when (action) {
                "poweroff" -> "poweroff"
                "recovery" -> "reboot recovery"
                "bootloader" -> "reboot bootloader"
                else -> "reboot"
            }

            try {
                val proc = Runtime.getRuntime().exec(arrayOf("su", "-c", cmd))
                proc.waitFor()
            } catch (ex: Exception) {
                telegramApi.sendMessage("Failed to execute root command: ${ex.message}", chatId)
            }
        } catch (_: Exception) {
        }
    }

                                                    kotlinx.coroutines.delay(5000)
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                                            }
        val channelId = "tg_poll"

                                        // register network callback to trigger queue processing when connectivity returns and send status messages
                                        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                                        try {
                                            cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                                                override fun onAvailable(network: Network) {
                                                    // network restored
                                                    val prefs2 = getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
                                                    val lostAt = prefs2.getLong("network_lost_at", -1L)
                                                    if (lostAt > 0) {
                                                        val dur = System.currentTimeMillis() - lostAt
                                                        val mins = dur / 60000
                                                        telegramApi.sendMessage("📶 Network Restored\nOffline Duration: ${mins}m\nPending Messages: ${try { pendingDao.getPending().size } catch (_: Exception) { 0 }}")
                                                        prefs2.edit().remove("network_lost_at").apply()
                                                    }
                                                    // trigger immediate queue worker
                                                    val work = OneTimeWorkRequestBuilder<QueueWorker>().build()
                                                    WorkManager.getInstance(applicationContext).enqueue(work)
                                                }

                                                override fun onLost(network: Network) {
                                                    // store lost timestamp
                                                    getSharedPreferences("secure_prefs", Context.MODE_PRIVATE).edit().putLong("network_lost_at", System.currentTimeMillis()).apply()
                                                }

                                                override fun onCapabilitiesChanged(network: Network, networkCapabilities: android.net.NetworkCapabilities) {
                                                    try {
                                                        val prev = getSharedPreferences("secure_prefs", Context.MODE_PRIVATE).getString("last_network_type", "")
                                                        val current = when {
                                                            networkCapabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
                                                            networkCapabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                                                                val tm2 = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                                                                tm2.networkOperatorName ?: "Mobile"
                                                            }
                                                            else -> "Other"
                                                        }
                                                        if (current != prev) {
                                                            getSharedPreferences("secure_prefs", Context.MODE_PRIVATE).edit().putString("last_network_type", current).apply()
                                                            telegramApi.sendMessage("📡 Network Changed\nPrevious: ${prev}\nCurrent: ${current}")
                                                        }
                                                    } catch (_: Exception) {
                                                    }
                                                }
                                            })
                                        } catch (_: Exception) {
                                        }

                                        // battery monitoring receiver
                                        try {
                                            val battReceiver = object : BroadcastReceiver() {
                                                override fun onReceive(context: Context?, intent: Intent?) {
                                                    try {
                                                        if (intent == null) return
                                                        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                                                        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
                                                        val percent = if (level >= 0 && scale > 0) (level * 100 / scale) else -1
                                                        val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) > 0
                                                        val tempRaw = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)
                                                        val temp = tempRaw / 10.0f

                                                        val prefs2 = getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
                                                        val lastLevel = prefs2.getInt("last_batt_level", -1)
                                                        val lastPlugged = prefs2.getBoolean("last_batt_plugged", false)

                                                        val thresholds = listOf(30, 20, 10, 5)
                                                        for (t in thresholds) {
                                                            if (percent <= t && (lastLevel == -1 || lastLevel > t)) {
                                                                telegramApi.sendMessage("🔋 Battery Warning\nBattery: ${percent}%\nCharging: ${if (plugged) "Yes" else "No"}\nTemperature: ${String.format(Locale.getDefault(), "%.1f°C", temp)}\nThreshold: ${t}%")
                                                                break
                                                            }
                                                        }

                                                        if (plugged != lastPlugged) {
                                                            if (plugged) telegramApi.sendMessage("🔌 Charging Started\nBattery: ${percent}%") else telegramApi.sendMessage("🔌 Charging Stopped\nBattery: ${percent}%")
                                                            prefs2.edit().putBoolean("last_batt_plugged", plugged).apply()
                                                        }

                                                        if (temp >= 42.0f) {
                                                            telegramApi.sendMessage("🌡 Temperature Warning\nBattery Temperature: ${String.format(Locale.getDefault(), "%.1f°C", temp)}\nThreshold: 42°C")
                                                        }

                                                        prefs2.edit().putInt("last_batt_level", percent).apply()
                                                    } catch (_: Exception) {
                                                    }
                                                }
                                            }
                                            registerReceiver(battReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                                        } catch (_: Exception) {
                                        }
