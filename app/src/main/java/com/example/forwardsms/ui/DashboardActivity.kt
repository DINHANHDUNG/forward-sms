package com.example.forwardsms.ui

import android.os.BatteryManager
import android.content.Intent
import android.widget.Button
import android.os.Bundle
import android.telephony.TelephonyManager
import android.net.ConnectivityManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.forwardsms.R
import android.view.View
import android.content.pm.PackageManager
import android.Manifest
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.widget.Toast

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val tvBattery = findViewById<TextView>(R.id.tvBattery)
        val tvSim = findViewById<TextView>(R.id.tvSim)
        val tvNetwork = findViewById<TextView>(R.id.tvNetwork)
        val btnSettings = findViewById<Button>(R.id.btnSettings)
        val btnGrant = findViewById<Button>(R.id.btnGrantPermissions)

        val bm = getSystemService(BATTERY_SERVICE) as BatteryManager
        val battery = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        tvBattery.text = "Battery: ${battery}%"

        val tm = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val simActive = if (tm.simState == TelephonyManager.SIM_STATE_READY) "Active" else "Inactive"
        tvSim.text = "SIM: $simActive"

        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        tvNetwork.text = "Network: ${if (cm.activeNetwork != null) "Connected" else "Disconnected"}"

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        fun hasPermissions(): Boolean {
            val perms = arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE)
            for (p in perms) {
                if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) return false
            }
            return true
        }

        if (hasPermissions()) btnGrant.visibility = View.GONE else btnGrant.visibility = View.VISIBLE

        btnGrant.setOnClickListener {
            val perms = arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE)
            ActivityCompat.requestPermissions(this, perms, PERM_REQ)
        }
    }

    private val PERM_REQ = 1001

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERM_REQ) {
            val all = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            Toast.makeText(this, if (all) "Permissions granted" else "Permissions not granted", Toast.LENGTH_SHORT).show()
        }
    }
}
