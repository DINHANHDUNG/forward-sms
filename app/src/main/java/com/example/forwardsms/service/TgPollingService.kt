package com.example.forwardsms.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
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
                                                telegramApi.sendMessage("✅ SMS Sent\nTo:${phone}\nMessage:${replyText}")
                                                // store log (no content in logs)
                                                logDao.insert(com.example.forwardsms.data.entities.LogEntry(type = "OUTGOING_SMS", message = "To:$phone"))
                                            } catch (ex: Exception) {
                                                telegramApi.sendMessage("Failed to send SMS to ${phone}")
                                            }
                                        } else {
                                            telegramApi.sendMessage("Reply failed: unknown target $target")
                                        }
                                    } else {
                                        telegramApi.sendMessage("Usage: /reply <message_ref_or_phone> <text>")
                                    }
                                }
                            }
                        }
                    }
                } catch (ex: Exception) {
                    // network or parsing error - wait and continue
                    kotlinx.coroutines.delay(5000)
                }
            }
        }

        // register network callback to trigger queue processing when connectivity returns
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    // trigger immediate queue worker
                    val work = OneTimeWorkRequestBuilder<QueueWorker>().build()
                    WorkManager.getInstance(applicationContext).enqueue(work)
                }
            })
        } catch (_: Exception) {
        }
    }

    override fun onBind(intent: Intent?) = null

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun createNotification(): Notification {
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "tg_poll"
        if (nm.getNotificationChannel(channelId) == null) {
            val ch = NotificationChannel(channelId, "Telegram Polling", NotificationManager.IMPORTANCE_LOW)
            nm.createNotificationChannel(ch)
        }
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Forward SMS")
            .setContentText("Telegram polling active")
            .setSmallIcon(android.R.drawable.sym_action_chat)
            .setOngoing(true)
            .build()
    }
}
