package com.example.forwardsms.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.forwardsms.data.entities.PendingMessage

@Dao
interface PendingMessageDao {
    @Insert
    suspend fun insert(msg: PendingMessage): Long

    @Query("SELECT * FROM pending_messages WHERE status = 'PENDING' ORDER BY createdAt ASC")
    suspend fun getPending(): List<PendingMessage>

    @Update
    suspend fun update(msg: PendingMessage)

    @Query("UPDATE pending_messages SET status = :status, retryCount = :retryCount, sentAt = :sentAt WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String, retryCount: Int, sentAt: Long?)

    @Query("DELETE FROM pending_messages WHERE id = :id")
    suspend fun deleteById(id: Long)
}
