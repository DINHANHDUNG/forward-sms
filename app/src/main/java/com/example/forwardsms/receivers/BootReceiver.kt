package com.example.forwardsms.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.forwardsms.service.TgPollingService
import com.example.forwardsms.work.HeartbeatWorker
import com.example.forwardsms.work.SimPingWorker
import com.example.forwardsms.work.SmsRecoveryWorker
import java.util.concurrent.TimeUnit

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Start the foreground polling service
            val svc = Intent(context, TgPollingService::class.java)
            context.startForegroundService(svc)

            val wm = WorkManager.getInstance(context)

            // Heartbeat every 30 days
            val heartbeat = PeriodicWorkRequestBuilder<HeartbeatWorker>(30, TimeUnit.DAYS).build()
            wm.enqueue(heartbeat)

            // SIM ping every 30 days
            val simPing = PeriodicWorkRequestBuilder<SimPingWorker>(30, TimeUnit.DAYS).build()
            wm.enqueue(simPing)

            // Run an immediate SMS recovery scan after boot
            val recovery = OneTimeWorkRequestBuilder<SmsRecoveryWorker>().build()
            wm.enqueue(recovery)
        }
    }
}
