package com.example.forwardsms.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.forwardsms.R
import com.example.forwardsms.data.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logs)

        val tv = findViewById<TextView>(R.id.tvLogs)
        val db = AppDatabase::class.java
        val instance = androidx.room.Room.databaseBuilder(applicationContext, com.example.forwardsms.data.db.AppDatabase::class.java, "forward_sms_db").build()

        lifecycleScope.launch {
            val logs = withContext(Dispatchers.IO) { instance.logDao().getLatest500() }
            val sb = StringBuilder()
            for (l in logs) {
                sb.append("[").append(java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(java.util.Date(l.timestamp))).append("] ").append(l.type).append(": ").append(l.message).append("\n")
            }
            tv.text = sb.toString()
        }
    }
}
