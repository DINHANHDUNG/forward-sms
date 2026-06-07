package com.example.forwardsms.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.forwardsms.data.dao.ForwardedSmsDao
import com.example.forwardsms.repository.MessageRepository
import android.util.Log
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ProcessIncomingSmsWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repo: MessageRepository,
    private val forwardedDao: ForwardedSmsDao
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val sender = inputData.getString("sender") ?: return Result.success()
        val senderName = inputData.getString("senderName")
        val timestamp = inputData.getLong("timestamp", System.currentTimeMillis())
        val body = inputData.getString("body") ?: ""

        val hash = repo.computeHash(sender, timestamp, body)
        val exists = forwardedDao.existsByHash(hash) > 0
        Log.i("ProcessIncomingSms", "Processing SMS from=$sender exists=$exists")
        if (exists) {
            return Result.success()
        }

        // Attempt forwarding (will enqueue on failure)
        val ok = repo.forwardSms(sender, if (senderName.isNullOrEmpty()) null else senderName, timestamp, body)
        Log.i("ProcessIncomingSms", "forwardSms result=$ok for sender=$sender")

        return Result.success()
    }
}
