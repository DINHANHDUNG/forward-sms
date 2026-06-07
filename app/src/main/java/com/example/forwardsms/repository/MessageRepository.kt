package com.example.forwardsms.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import com.example.forwardsms.data.dao.ForwardedSmsDao
import com.example.forwardsms.data.dao.LogDao
import com.example.forwardsms.data.dao.PendingMessageDao
import com.example.forwardsms.data.entities.ForwardedSms
import com.example.forwardsms.data.entities.LogEntry
import com.example.forwardsms.data.entities.PendingMessage
import com.example.forwardsms.telegram.TelegramApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val api: TelegramApi,
    private val pendingDao: PendingMessageDao,
    private val logDao: LogDao,
    private val forwardedDao: ForwardedSmsDao,
    @ApplicationContext private val context: Context
) {

    suspend fun forwardSms(sender: String, senderName: String?, timestamp: Long, body: String): Boolean {
        return withContext(Dispatchers.IO) {
            val displayName = senderName ?: sender
            val time = formatDate(timestamp)
            val text = StringBuilder()
                .append("📩 SMS Received\n")
                .append("Sender: ").append(displayName).append("\n")
                .append("Phone: ").append(sender).append("\n")
                .append("Time: ").append(time).append("\n")
                .append("Message: ").append(body)
                .toString()

            try {
                val smsHash = computeHash(sender, timestamp, body)
                if (forwardedDao.existsByHash(smsHash) > 0) {
                    // already forwarded
                    return@withContext true
                }

                val messageRef = "MSG-" + java.util.UUID.randomUUID().toString().substring(0, 8)
                val textWithId = text + "\nID: " + messageRef

                val ok = api.sendMessage(textWithId)
                if (ok) {
                    // record forwarded hash and reference (do not log contents)
                    forwardedDao.insert(ForwardedSms(smsId = null, sender = sender, timestamp = timestamp, smsHash = smsHash, messageRef = messageRef))
                    logDao.insert(LogEntry(type = "SMS_FORWARDED", message = "From:$sender"))
                    true
                } else {
                    // queue
                    val payload = JSONObject().put("type","sms").put("sender", sender).put("senderName", senderName ?: "").put("timestamp", timestamp).put("body", body).put("messageRef", messageRef).toString()
                    pendingDao.insert(PendingMessage(type = "SMS", payload = payload))
                    logDao.insert(LogEntry(type = "QUEUE_ADD", message = "Queued SMS from $sender"))
                    false
                }
            } catch (ex: Exception) {
                val messageRef = "MSG-" + java.util.UUID.randomUUID().toString().substring(0, 8)
                val payload = JSONObject().put("type","sms").put("sender", sender).put("senderName", senderName ?: "").put("timestamp", timestamp).put("body", body).put("messageRef", messageRef).toString()
                pendingDao.insert(PendingMessage(type = "SMS", payload = payload))
                logDao.insert(LogEntry(type = "QUEUE_ADD", message = "Queued SMS from $sender due to error"))
                false
            }
        }
    }

    suspend fun forwardMissedCall(phone: String, timestamp: Long) : Boolean {
        return withContext(Dispatchers.IO) {
            val time = formatDate(timestamp)
            val text = StringBuilder()
                .append("📞 Missed Call\n")
                .append("Phone: ").append(phone).append("\n")
                .append("Time: ").append(time)
                .toString()

            try {
                val ok = api.sendMessage(text)
                if (ok) {
                    logDao.insert(LogEntry(type = "CALL_FORWARDED", message = "Missed call from $phone"))
                    true
                } else {
                    val payload = JSONObject().put("type","call").put("phone", phone).put("timestamp", timestamp).toString()
                    pendingDao.insert(PendingMessage(type = "CALL", payload = payload))
                    logDao.insert(LogEntry(type = "QUEUE_ADD", message = "Queued missed call $phone"))
                    false
                }
            } catch (ex: Exception) {
                val payload = JSONObject().put("type","call").put("phone", phone).put("timestamp", timestamp).toString()
                pendingDao.insert(PendingMessage(type = "CALL", payload = payload))
                logDao.insert(LogEntry(type = "QUEUE_ADD", message = "Queued missed call $phone due to error"))
                false
            }
        }
    }

    suspend fun enqueueHeartbeat(payload: String) {
        withContext(Dispatchers.IO) {
            pendingDao.insert(PendingMessage(type = "HEARTBEAT", payload = payload))
            logDao.insert(LogEntry(type = "QUEUE_ADD", message = "Queued heartbeat"))
        }
    }

    private fun formatDate(ts: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(Date(ts))
    }

    fun computeHash(sender: String, timestamp: Long, content: String): String {
        val data = "$sender|$timestamp|$content"
        val digest = java.security.MessageDigest.getInstance("SHA-256").digest(data.toByteArray(Charsets.UTF_8))
        return digest.joinToString("") { "%02x".format(it) }
    }
}
