package com.example.forwardsms.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B1\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0001\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u001e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000eJ\u0019\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0010\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0011H\u0002J!\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001cJ3\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u000e2\b\u0010\u001e\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006!"}, d2 = {"Lcom/example/forwardsms/repository/MessageRepository;", "", "api", "Lcom/example/forwardsms/telegram/TelegramApi;", "pendingDao", "Lcom/example/forwardsms/data/dao/PendingMessageDao;", "logDao", "Lcom/example/forwardsms/data/dao/LogDao;", "forwardedDao", "Lcom/example/forwardsms/data/dao/ForwardedSmsDao;", "context", "Landroid/content/Context;", "(Lcom/example/forwardsms/telegram/TelegramApi;Lcom/example/forwardsms/data/dao/PendingMessageDao;Lcom/example/forwardsms/data/dao/LogDao;Lcom/example/forwardsms/data/dao/ForwardedSmsDao;Landroid/content/Context;)V", "computeHash", "", "sender", "timestamp", "", "content", "enqueueHeartbeat", "", "payload", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "formatDate", "ts", "forwardMissedCall", "", "phone", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "forwardSms", "senderName", "body", "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public final class MessageRepository {
    @org.jetbrains.annotations.NotNull
    private final com.example.forwardsms.telegram.TelegramApi api = null;
    @org.jetbrains.annotations.NotNull
    private final com.example.forwardsms.data.dao.PendingMessageDao pendingDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.example.forwardsms.data.dao.LogDao logDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.example.forwardsms.data.dao.ForwardedSmsDao forwardedDao = null;
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    
    @javax.inject.Inject
    public MessageRepository(@org.jetbrains.annotations.NotNull
    com.example.forwardsms.telegram.TelegramApi api, @org.jetbrains.annotations.NotNull
    com.example.forwardsms.data.dao.PendingMessageDao pendingDao, @org.jetbrains.annotations.NotNull
    com.example.forwardsms.data.dao.LogDao logDao, @org.jetbrains.annotations.NotNull
    com.example.forwardsms.data.dao.ForwardedSmsDao forwardedDao, @dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object forwardSms(@org.jetbrains.annotations.NotNull
    java.lang.String sender, @org.jetbrains.annotations.Nullable
    java.lang.String senderName, long timestamp, @org.jetbrains.annotations.NotNull
    java.lang.String body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object forwardMissedCall(@org.jetbrains.annotations.NotNull
    java.lang.String phone, long timestamp, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object enqueueHeartbeat(@org.jetbrains.annotations.NotNull
    java.lang.String payload, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String formatDate(long ts) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String computeHash(@org.jetbrains.annotations.NotNull
    java.lang.String sender, long timestamp, @org.jetbrains.annotations.NotNull
    java.lang.String content) {
        return null;
    }
}