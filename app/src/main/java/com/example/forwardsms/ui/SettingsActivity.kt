package com.example.forwardsms.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Switch
import android.widget.TextView
import android.app.TimePickerDialog
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.Context
import java.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import com.example.forwardsms.R
import com.example.forwardsms.telegram.TelegramApi
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {
    private val vm: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val etToken = findViewById<EditText>(R.id.etToken)
        val etChatId = findViewById<EditText>(R.id.etChatId)
        val switchReboot = findViewById<Switch>(R.id.switchRebootEnabled)
        val spinnerType = findViewById<Spinner>(R.id.spinnerRebootType)
        val tvRebootTime = findViewById<TextView>(R.id.tvRebootTime)
        val btnPickTime = findViewById<Button>(R.id.btnPickTime)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnTest = findViewById<Button>(R.id.btnTest)
        val btnSendTest = findViewById<Button>(R.id.btnSendTest)

        etToken.setText(vm.getToken())
        etChatId.setText(vm.getChatId())
        switchReboot.isChecked = vm.getRebootEnabled()

        val types = listOf("daily", "weekly", "monthly")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOf("Daily", "Weekly", "Monthly"))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType.adapter = adapter
        val curType = vm.getRebootScheduleType() ?: "weekly"
        spinnerType.setSelection(types.indexOf(curType).coerceAtLeast(0))

        var curTime = vm.getRebootScheduleTime() ?: "03:00"
        tvRebootTime.text = "Time: $curTime"

        btnPickTime.setOnClickListener {
            try {
                val parts = curTime.split(":")
                val h = parts[0].toInt()
                val m = parts[1].toInt()
                val tp = TimePickerDialog(this, { _, hourOfDay, minute ->
                    curTime = String.format("%02d:%02d", hourOfDay, minute)
                    tvRebootTime.text = "Time: $curTime"
                }, h, m, true)
                tp.show()
            } catch (ex: Exception) {
            }
        }

        btnSave.setOnClickListener {
            vm.save(etToken.text.toString().trim(), etChatId.text.toString().trim())
            // save reboot schedule settings
            val enabled = switchReboot.isChecked
            val sel = spinnerType.selectedItemPosition
            val type = when (sel) {
                0 -> "daily"
                1 -> "weekly"
                else -> "monthly"
            }
            vm.saveRebootSchedule(type, curTime)
            vm.setRebootEnabled(enabled)

            if (enabled) scheduleReboot(type, curTime) else cancelScheduledReboot()

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
        }

        btnTest.setOnClickListener {
            lifecycleScope.launch {
                val ok = vm.testConnection()
                Toast.makeText(this@SettingsActivity, if (ok) "Connected" else "Connection failed", Toast.LENGTH_SHORT).show()
            }
        }

        btnSendTest.setOnClickListener {
            lifecycleScope.launch {
                val ok = vm.sendTestMessage("🔔 Test message from Forward SMS app")
                Toast.makeText(this@SettingsActivity, if (ok) "Test message sent" else "Test message failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun scheduleReboot(type: String, time: String) {
        try {
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

            val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val warnIntent = Intent(this, com.example.forwardsms.receivers.ScheduledRebootReceiver::class.java).apply {
                putExtra("type", "warning")
                putExtra("action", "reboot")
            }
            val warnPi = PendingIntent.getBroadcast(this, 1001, warnIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            val execIntent = Intent(this, com.example.forwardsms.receivers.ScheduledRebootReceiver::class.java).apply {
                putExtra("type", "execute")
                putExtra("action", "reboot")
            }
            val execPi = PendingIntent.getBroadcast(this, 1002, execIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next.timeInMillis - 60_000L, warnPi)
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next.timeInMillis, execPi)
            Toast.makeText(this, "Reboot scheduled", Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(this, "Failed to schedule reboot: ${ex.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun cancelScheduledReboot() {
        try {
            val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val warnIntent = Intent(this, com.example.forwardsms.receivers.ScheduledRebootReceiver::class.java).apply {
                putExtra("type", "warning")
                putExtra("action", "reboot")
            }
            val execIntent = Intent(this, com.example.forwardsms.receivers.ScheduledRebootReceiver::class.java).apply {
                putExtra("type", "execute")
                putExtra("action", "reboot")
            }
            val warnPi = PendingIntent.getBroadcast(this, 1001, warnIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            val execPi = PendingIntent.getBroadcast(this, 1002, execIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            am.cancel(warnPi)
            am.cancel(execPi)
            Toast.makeText(this, "Scheduled reboot cancelled", Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(this, "Failed to cancel schedule: ${ex.message}", Toast.LENGTH_LONG).show()
        }
    }
}
