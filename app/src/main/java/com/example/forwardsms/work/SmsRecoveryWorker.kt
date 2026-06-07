package com.example.forwardsms.work

import android.content.Context
import android.net.Uri
import android.provider.Telephony
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.forwardsms.data.dao.ForwardedSmsDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SmsRecoveryWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val forwardedDao: ForwardedSmsDao
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val sevenDaysAgo = System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000
            val uri = Uri.parse("content://sms/inbox")
            val projection = arrayOf("_id", "address", "date", "body")
            val selection = "date >= ?"
            val selectionArgs = arrayOf(sevenDaysAgo.toString())
            val cr = applicationContext.contentResolver
            cr.query(uri, projection, selection, selectionArgs, "date ASC")?.use { cursor ->
                val idIdx = cursor.getColumnIndex("_id")
                val addrIdx = cursor.getColumnIndex("address")
                val dateIdx = cursor.getColumnIndex("date")
                val bodyIdx = cursor.getColumnIndex("body")
                while (cursor.moveToNext()) {
                    val id = if (idIdx >= 0) cursor.getString(idIdx) else null
                    val addr = if (addrIdx >= 0) cursor.getString(addrIdx) else ""
                    val date = if (dateIdx >= 0) cursor.getLong(dateIdx) else System.currentTimeMillis()
                    val body = if (bodyIdx >= 0) cursor.getString(bodyIdx) ?: "" else ""
                    // compute simple hash
                    val hash = repoComputeHash(addr, date, body)
                    val exists = forwardedDao.existsByHash(hash) > 0
                    if (!exists) {
                        val data = androidx.work.Data.Builder()
                            .putString("sender", addr)
                            .putString("senderName", "")
                            .putLong("timestamp", date)
                            .putString("body", body)
                            .build()

                        val work = OneTimeWorkRequestBuilder<ProcessIncomingSmsWorker>()
                            .setInputData(data)
                            .build()

                        WorkManager.getInstance(applicationContext).enqueue(work)
                    }
                }
            }
            Result.success()
        } catch (ex: Exception) {
            Result.retry()
        }
    }

    private fun repoComputeHash(sender: String, timestamp: Long, content: String): String {
        val data = "$sender|$timestamp|$content"
        val digest = java.security.MessageDigest.getInstance("SHA-256").digest(data.toByteArray(Charsets.UTF_8))
        return digest.joinToString("") { "%02x".format(it) }
    }
}
