package com.example.forwardsms.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnTest = findViewById<Button>(R.id.btnTest)
        val btnSendTest = findViewById<Button>(R.id.btnSendTest)

        etToken.setText(vm.getToken())
        etChatId.setText(vm.getChatId())

        btnSave.setOnClickListener {
            vm.save(etToken.text.toString().trim(), etChatId.text.toString().trim())
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
}
