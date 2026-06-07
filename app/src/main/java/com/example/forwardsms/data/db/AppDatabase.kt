package com.example.forwardsms.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.forwardsms.data.dao.ForwardedSmsDao
import com.example.forwardsms.data.dao.LogDao
import com.example.forwardsms.data.dao.PendingMessageDao
import com.example.forwardsms.data.entities.ForwardedSms
import com.example.forwardsms.data.entities.LogEntry
import com.example.forwardsms.data.entities.PendingMessage

@Database(entities = [PendingMessage::class, LogEntry::class, ForwardedSms::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pendingMessageDao(): PendingMessageDao
    abstract fun logDao(): LogDao
    abstract fun forwardedSmsDao(): ForwardedSmsDao
}
