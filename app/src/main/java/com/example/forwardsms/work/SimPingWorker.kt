package com.example.forwardsms.work

import android.content.Context
import android.telephony.SmsManager
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.forwardsms.telegram.TelegramApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltWorker
class SimPingWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val prefs: SharedPreferences
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val PREF_SIM_PING_NUMBER = "sim_ping_number"
        private const val PREF_SIM_PING_MESSAGE = "sim_ping_message"
    }

    override suspend fun doWork(): Result {
        return try {
            val number = prefs.getString(PREF_SIM_PING_NUMBER, null)
            val message = prefs.getString(PREF_SIM_PING_MESSAGE, "PING") ?: "PING"
            if (number.isNullOrBlank()) return Result.success()
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(number, null, message, null, null)
            Result.success()
        } catch (ex: Exception) {
            Result.retry()
        }
    }
}
