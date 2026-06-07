package com.example.forwardsms.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.room.Room
import com.example.forwardsms.data.db.AppDatabase
import com.example.forwardsms.telegram.TelegramApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val hostEnforcer = Interceptor { chain ->
            val request = chain.request()
            val host = request.url.host
            if (host != "api.telegram.org") {
                throw IllegalStateException("Only api.telegram.org is allowed")
            }
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .callTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(hostEnforcer)
            .build()
    }

    @Provides
    @Singleton
    fun provideTelegramApi(client: OkHttpClient, prefs: SharedPreferences): TelegramApi {
        return TelegramApi(client, prefs)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "forward_sms_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePendingMessageDao(db: AppDatabase) = db.pendingMessageDao()

    @Provides
    fun provideLogDao(db: AppDatabase) = db.logDao()

    @Provides
    fun provideForwardedSmsDao(db: AppDatabase) = db.forwardedSmsDao()
}
