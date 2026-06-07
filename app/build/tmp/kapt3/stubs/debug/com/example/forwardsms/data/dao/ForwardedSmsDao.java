package com.example.forwardsms.data.dao;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u001b\u0010\f\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0011"}, d2 = {"Lcom/example/forwardsms/data/dao/ForwardedSmsDao;", "", "existsByHash", "", "hash", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getById", "Lcom/example/forwardsms/data/entities/ForwardedSms;", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByMessageRef", "ref", "insert", "item", "(Lcom/example/forwardsms/data/entities/ForwardedSms;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface ForwardedSmsDao {
    
    @androidx.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.example.forwardsms.data.entities.ForwardedSms item, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM forwarded_sms WHERE smsHash = :hash LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object existsByHash(@org.jetbrains.annotations.NotNull
    java.lang.String hash, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM forwarded_sms WHERE id = :id LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.forwardsms.data.entities.ForwardedSms> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM forwarded_sms WHERE messageRef = :ref LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getByMessageRef(@org.jetbrains.annotations.NotNull
    java.lang.String ref, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.forwardsms.data.entities.ForwardedSms> $completion);
}