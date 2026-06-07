package com.example.forwardsms.work

import android.content.Context
import android.net.ConnectivityManager
import android.os.BatteryManager
import android.telephony.TelephonyManager
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.forwardsms.telegram.TelegramApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class HeartbeatWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val telegramApi: TelegramApi
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val bm = applicationContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            val battery = (bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY))

            val tm = applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val simState = tm.simState
            val simActive = when (simState) {
                TelephonyManager.SIM_STATE_READY -> "Active"
                else -> "Inactive"
            }

            val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = if (cm.activeNetwork != null) "Connected" else "Disconnected"

            val date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())

            val text = StringBuilder()
                .append("❤️ Device Status\n")
                .append("Device Online\n")
                .append("Battery: ").append(battery).append("%\n")
                .append("SIM: ").append(simActive).append("\n")
                .append("Network: ").append(network).append("\n")
                .append("Date: ").append(date)
                .toString()

            val ok = telegramApi.sendMessage(text)
            if (ok) Result.success() else Result.retry()
        } catch (ex: Exception) {
            Result.retry()
        }
    }
}
