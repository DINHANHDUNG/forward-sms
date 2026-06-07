package com.example.forwardsms.work;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.example.forwardsms.telegram.TelegramApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class HeartbeatWorker_Factory {
  private final Provider<TelegramApi> telegramApiProvider;

  public HeartbeatWorker_Factory(Provider<TelegramApi> telegramApiProvider) {
    this.telegramApiProvider = telegramApiProvider;
  }

  public HeartbeatWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, telegramApiProvider.get());
  }

  public static HeartbeatWorker_Factory create(Provider<TelegramApi> telegramApiProvider) {
    return new HeartbeatWorker_Factory(telegramApiProvider);
  }

  public static HeartbeatWorker newInstance(Context appContext, WorkerParameters workerParams,
      TelegramApi telegramApi) {
    return new HeartbeatWorker(appContext, workerParams, telegramApi);
  }
}
