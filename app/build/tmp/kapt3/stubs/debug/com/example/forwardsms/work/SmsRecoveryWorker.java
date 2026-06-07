package com.example.forwardsms.work;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B#\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0011\u0010\t\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\rH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0012"}, d2 = {"Lcom/example/forwardsms/work/SmsRecoveryWorker;", "Landroidx/work/CoroutineWorker;", "appContext", "Landroid/content/Context;", "workerParams", "Landroidx/work/WorkerParameters;", "forwardedDao", "Lcom/example/forwardsms/data/dao/ForwardedSmsDao;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;Lcom/example/forwardsms/data/dao/ForwardedSmsDao;)V", "doWork", "Landroidx/work/ListenableWorker$Result;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "repoComputeHash", "", "sender", "timestamp", "", "content", "app_debug"})
@androidx.hilt.work.HiltWorker
public final class SmsRecoveryWorker extends androidx.work.CoroutineWorker {
    @org.jetbrains.annotations.NotNull
    private final com.example.forwardsms.data.dao.ForwardedSmsDao forwardedDao = null;
    
    @dagger.assisted.AssistedInject
    public SmsRecoveryWorker(@dagger.assisted.Assisted
    @org.jetbrains.annotations.NotNull
    android.content.Context appContext, @dagger.assisted.Assisted
    @org.jetbrains.annotations.NotNull
    androidx.work.WorkerParameters workerParams, @org.jetbrains.annotations.NotNull
    com.example.forwardsms.data.dao.ForwardedSmsDao forwardedDao) {
        super(null, null);
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object doWork(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super androidx.work.ListenableWorker.Result> $completion) {
        return null;
    }
    
    private final java.lang.String repoComputeHash(java.lang.String sender, long timestamp, java.lang.String content) {
        return null;
    }
}