package com.example.forwardsms.data.db;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\t"}, d2 = {"Lcom/example/forwardsms/data/db/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "forwardedSmsDao", "Lcom/example/forwardsms/data/dao/ForwardedSmsDao;", "logDao", "Lcom/example/forwardsms/data/dao/LogDao;", "pendingMessageDao", "Lcom/example/forwardsms/data/dao/PendingMessageDao;", "app_release"})
@androidx.room.Database(entities = {com.example.forwardsms.data.entities.PendingMessage.class, com.example.forwardsms.data.entities.LogEntry.class, com.example.forwardsms.data.entities.ForwardedSms.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public abstract com.example.forwardsms.data.dao.PendingMessageDao pendingMessageDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.example.forwardsms.data.dao.LogDao logDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.example.forwardsms.data.dao.ForwardedSmsDao forwardedSmsDao();
}