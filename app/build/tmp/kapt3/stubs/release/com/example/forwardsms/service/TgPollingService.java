package com.example.forwardsms.service;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\u001f\u001a\u00020 H\u0016J\b\u0010!\u001a\u00020 H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0013\u001a\u00020\u00148\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018\u00a8\u0006\""}, d2 = {"Lcom/example/forwardsms/service/TgPollingService;", "Landroid/app/Service;", "()V", "forwardedDao", "Lcom/example/forwardsms/data/dao/ForwardedSmsDao;", "getForwardedDao", "()Lcom/example/forwardsms/data/dao/ForwardedSmsDao;", "setForwardedDao", "(Lcom/example/forwardsms/data/dao/ForwardedSmsDao;)V", "logDao", "Lcom/example/forwardsms/data/dao/LogDao;", "getLogDao", "()Lcom/example/forwardsms/data/dao/LogDao;", "setLogDao", "(Lcom/example/forwardsms/data/dao/LogDao;)V", "offset", "", "scope", "Lkotlinx/coroutines/CoroutineScope;", "telegramApi", "Lcom/example/forwardsms/telegram/TelegramApi;", "getTelegramApi", "()Lcom/example/forwardsms/telegram/TelegramApi;", "setTelegramApi", "(Lcom/example/forwardsms/telegram/TelegramApi;)V", "createNotification", "Landroid/app/Notification;", "onBind", "", "intent", "Landroid/content/Intent;", "onCreate", "", "onDestroy", "app_release"})
public final class TgPollingService extends android.app.Service {
    @javax.inject.Inject
    public com.example.forwardsms.telegram.TelegramApi telegramApi;
    @javax.inject.Inject
    public com.example.forwardsms.data.dao.ForwardedSmsDao forwardedDao;
    @javax.inject.Inject
    public com.example.forwardsms.data.dao.LogDao logDao;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope scope = null;
    private long offset = 0L;
    
    public TgPollingService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.forwardsms.telegram.TelegramApi getTelegramApi() {
        return null;
    }
    
    public final void setTelegramApi(@org.jetbrains.annotations.NotNull
    com.example.forwardsms.telegram.TelegramApi p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.forwardsms.data.dao.ForwardedSmsDao getForwardedDao() {
        return null;
    }
    
    public final void setForwardedDao(@org.jetbrains.annotations.NotNull
    com.example.forwardsms.data.dao.ForwardedSmsDao p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.forwardsms.data.dao.LogDao getLogDao() {
        return null;
    }
    
    public final void setLogDao(@org.jetbrains.annotations.NotNull
    com.example.forwardsms.data.dao.LogDao p0) {
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Void onBind(@org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    private final android.app.Notification createNotification() {
        return null;
    }
}