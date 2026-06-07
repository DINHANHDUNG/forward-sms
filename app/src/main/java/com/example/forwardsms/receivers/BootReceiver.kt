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
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

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

            // reschedule scheduled reboot if enabled
            try {
                val prefs = context.getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
                val enabled = prefs.getBoolean("reboot_enabled", false)
                val type = prefs.getString("reboot_schedule_type", null)
                val time = prefs.getString("reboot_schedule_time", null)
                if (enabled && type != null && time != null) {
                    val parts = time.split(":")
                    val hour = parts[0].toInt()
                    val minute = parts[1].toInt()
                    val next = Calendar.getInstance()
                    next.set(Calendar.HOUR_OF_DAY, hour)
                    next.set(Calendar.MINUTE, minute)
                    next.set(Calendar.SECOND, 0)
                    next.set(Calendar.MILLISECOND, 0)
                    if (next.timeInMillis <= System.currentTimeMillis()) {
                        when (type) {
                            "daily" -> next.add(Calendar.DAY_OF_YEAR, 1)
                            "weekly" -> next.add(Calendar.WEEK_OF_YEAR, 1)
                            "monthly" -> next.add(Calendar.MONTH, 1)
                            else -> next.add(Calendar.WEEK_OF_YEAR, 1)
                        }
                    }

                    val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val warnIntent = Intent(context, com.example.forwardsms.receivers.ScheduledRebootReceiver::class.java).apply {
                        putExtra("type", "warning")
                        putExtra("action", "reboot")
                    }
                    val warnPi = PendingIntent.getBroadcast(context, 1001, warnIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                    val execIntent = Intent(context, com.example.forwardsms.receivers.ScheduledRebootReceiver::class.java).apply {
                        putExtra("type", "execute")
                        putExtra("action", "reboot")
                    }
                    val execPi = PendingIntent.getBroadcast(context, 1002, execIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                    am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next.timeInMillis - 60_000L, warnPi)
                    am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next.timeInMillis, execPi)
                }
            } catch (_: Exception) {
            }
        }
    }
}
