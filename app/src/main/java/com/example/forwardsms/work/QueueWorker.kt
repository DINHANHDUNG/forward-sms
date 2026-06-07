package com.example.forwardsms.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.forwardsms.data.dao.PendingMessageDao
import com.example.forwardsms.repository.MessageRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.concurrent.TimeUnit

@HiltWorker
class QueueWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val pendingDao: PendingMessageDao,
    private val repo: MessageRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val list = pendingDao.getPending()
            for (item in list) {
                try {
                    val obj = JSONObject(item.payload)
                    val type = obj.optString("type")
                    var success = false
                    when (type) {
                        "sms" -> {
                            val sender = obj.optString("sender")
                            val senderName = obj.optString("senderName")
                            val timestamp = obj.optLong("timestamp", System.currentTimeMillis())
                            val body = obj.optString("body")
                            success = repo.forwardSms(sender, if (senderName.isNullOrEmpty()) null else senderName, timestamp, body)
                        }
                        "call" -> {
                            val phone = obj.optString("phone")
                            val timestamp = obj.optLong("timestamp", System.currentTimeMillis())
                            success = repo.forwardMissedCall(phone, timestamp)
                        }
                        "heartbeat" -> {
                            val message = obj.optString("message")
                            success = repo.enqueueHeartbeat(message).let { true }
                        }
                        else -> {
                            // unknown type; mark as failed
                            success = false
                        }
                    }

                    if (success) {
                        pendingDao.updateStatus(item.id, "SENT", item.retryCount, System.currentTimeMillis())
                    } else {
                        // schedule retry according to strategy
                        val nextRetry = item.retryCount + 1
                        pendingDao.updateStatus(item.id, "PENDING", nextRetry, null)
                        val delayMinutes = when (nextRetry) {
                            1 -> 5L
                            2 -> 15L
                            3 -> 30L
                            else -> 60L
                        }
                        val work = OneTimeWorkRequestBuilder<QueueWorker>()
                            .setInitialDelay(delayMinutes, TimeUnit.MINUTES)
                            .build()
                        WorkManager.getInstance(applicationContext).enqueue(work)
                    }
                } catch (_: Exception) {
                    // ignore per-item exception, try next
                }
            }
            Result.success()
        } catch (ex: Exception) {
            Result.retry()
        }
    }
}
