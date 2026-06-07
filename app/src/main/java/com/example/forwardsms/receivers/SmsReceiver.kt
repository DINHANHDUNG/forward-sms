package com.example.forwardsms.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import androidx.work.Data
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.forwardsms.work.ProcessIncomingSmsWorker

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val bundle: Bundle? = intent.extras
            try {
                val pduObjects = bundle?.get("pdus") as? Array<*>
                val format = bundle?.getString("format")
                if (pduObjects != null) {
                    for (pdu in pduObjects) {
                        val sms = SmsMessage.createFromPdu(pdu as ByteArray, format)
                        val sender = sms.originatingAddress ?: ""
                        val body = sms.messageBody ?: ""
                        val timestamp = sms.timestampMillis

                        Log.i("SmsReceiver", "SMS received from=$sender len=${body.length}")

                        val data = Data.Builder()
                            .putString("sender", sender)
                            .putString("senderName", "")
                            .putLong("timestamp", timestamp)
                            .putString("body", body)
                            .build()

                        val work = OneTimeWorkRequestBuilder<ProcessIncomingSmsWorker>()
                            .setInputData(data)
                            .build()

                        WorkManager.getInstance(context).enqueue(work)
                    }
                }
            } catch (ex: Exception) {
                Log.e("SmsReceiver", "onReceive exception", ex)
            }
        }
    }
}
