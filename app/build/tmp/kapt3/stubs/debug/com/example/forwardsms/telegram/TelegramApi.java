package com.example.forwardsms.telegram;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0002J\n\u0010\n\u001a\u0004\u0018\u00010\bH\u0002J\n\u0010\u000b\u001a\u0004\u0018\u00010\bH\u0002J\u0010\u0010\f\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\bJ\u0006\u0010\u0012\u001a\u00020\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/example/forwardsms/telegram/TelegramApi;", "", "client", "Lokhttp3/OkHttpClient;", "prefs", "Landroid/content/SharedPreferences;", "(Lokhttp3/OkHttpClient;Landroid/content/SharedPreferences;)V", "extractToken", "", "raw", "getChatId", "getToken", "getUpdates", "offset", "", "sendMessage", "", "text", "testConnection", "Companion", "app_debug"})
public final class TelegramApi {
    @org.jetbrains.annotations.NotNull
    private final okhttp3.OkHttpClient client = null;
    @org.jetbrains.annotations.NotNull
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "TelegramApi";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String BASE = "https://api.telegram.org";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREF_BOT_TOKEN = "bot_token";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREF_CHAT_ID = "chat_id";
    @org.jetbrains.annotations.NotNull
    public static final com.example.forwardsms.telegram.TelegramApi.Companion Companion = null;
    
    @javax.inject.Inject
    public TelegramApi(@org.jetbrains.annotations.NotNull
    okhttp3.OkHttpClient client, @org.jetbrains.annotations.NotNull
    android.content.SharedPreferences prefs) {
        super();
    }
    
    private final java.lang.String extractToken(java.lang.String raw) {
        return null;
    }
    
    private final java.lang.String getToken() {
        return null;
    }
    
    private final java.lang.String getChatId() {
        return null;
    }
    
    public final boolean sendMessage(@org.jetbrains.annotations.NotNull
    java.lang.String text) {
        return false;
    }
    
    public final boolean testConnection() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getUpdates(long offset) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/example/forwardsms/telegram/TelegramApi$Companion;", "", "()V", "BASE", "", "PREF_BOT_TOKEN", "PREF_CHAT_ID", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}