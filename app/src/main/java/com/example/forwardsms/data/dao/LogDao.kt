package com.example.forwardsms.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.forwardsms.data.entities.LogEntry

@Dao
interface LogDao {
    @Insert
    suspend fun insert(log: LogEntry): Long

    @Query("SELECT * FROM logs ORDER BY timestamp DESC LIMIT 500")
    suspend fun getLatest500(): List<LogEntry>
}
