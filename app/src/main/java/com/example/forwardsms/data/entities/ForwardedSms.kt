package com.example.forwardsms.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "forwarded_sms", indices = [Index(value = ["smsHash"], unique = true)])
data class ForwardedSms(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val smsId: String?,
    val sender: String,
    val timestamp: Long,
    val smsHash: String,
    val messageRef: String? = null
)
