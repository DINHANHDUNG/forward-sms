package com.example.forwardsms.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.forwardsms.data.entities.ForwardedSms

@Dao
interface ForwardedSmsDao {
    @Insert
    suspend fun insert(item: ForwardedSms): Long

    @Query("SELECT COUNT(*) FROM forwarded_sms WHERE smsHash = :hash LIMIT 1")
    suspend fun existsByHash(hash: String): Int

    @Query("SELECT * FROM forwarded_sms WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ForwardedSms?

    @Query("SELECT * FROM forwarded_sms WHERE messageRef = :ref LIMIT 1")
    suspend fun getByMessageRef(ref: String): ForwardedSms?
}
