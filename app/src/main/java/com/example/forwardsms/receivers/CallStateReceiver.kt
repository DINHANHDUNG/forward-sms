package com.example.forwardsms.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.forwardsms.work.HandleMissedCallWorker

class CallStateReceiver : BroadcastReceiver() {
    companion object {
        private var lastState: String? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER) ?: ""

        if (state == TelephonyManager.EXTRA_STATE_RINGING) {
            lastState = TelephonyManager.EXTRA_STATE_RINGING
        } else if (state == TelephonyManager.EXTRA_STATE_IDLE) {
            if (lastState == TelephonyManager.EXTRA_STATE_RINGING) {
                // missed call
                val data = Data.Builder()
                    .putString("phone", incomingNumber)
                    .putLong("timestamp", System.currentTimeMillis())
                    .build()

                val work = OneTimeWorkRequestBuilder<HandleMissedCallWorker>()
                    .setInputData(data)
                    .build()

                WorkManager.getInstance(context).enqueue(work)
            }
            lastState = TelephonyManager.EXTRA_STATE_IDLE
        } else if (state == TelephonyManager.EXTRA_STATE_OFFHOOK) {
            lastState = TelephonyManager.EXTRA_STATE_OFFHOOK
        }
    }
}
