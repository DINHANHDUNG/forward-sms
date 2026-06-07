package com.example.forwardsms.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.forwardsms.repository.MessageRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class HandleMissedCallWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repo: MessageRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val phone = inputData.getString("phone") ?: return Result.success()
        val timestamp = inputData.getLong("timestamp", System.currentTimeMillis())

        repo.forwardMissedCall(phone, timestamp)
        return Result.success()
    }
}
