package com.example.forwardsms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_messages")
data class PendingMessage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String,
    val payload: String,
    val status: String = "PENDING",
    val retryCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val sentAt: Long? = null
)
