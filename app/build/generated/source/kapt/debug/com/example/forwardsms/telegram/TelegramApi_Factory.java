package com.example.forwardsms.telegram;

import android.content.SharedPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

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
public final class TelegramApi_Factory implements Factory<TelegramApi> {
  private final Provider<OkHttpClient> clientProvider;

  private final Provider<SharedPreferences> prefsProvider;

  public TelegramApi_Factory(Provider<OkHttpClient> clientProvider,
      Provider<SharedPreferences> prefsProvider) {
    this.clientProvider = clientProvider;
    this.prefsProvider = prefsProvider;
  }

  @Override
  public TelegramApi get() {
    return newInstance(clientProvider.get(), prefsProvider.get());
  }

  public static TelegramApi_Factory create(Provider<OkHttpClient> clientProvider,
      Provider<SharedPreferences> prefsProvider) {
    return new TelegramApi_Factory(clientProvider, prefsProvider);
  }

  public static TelegramApi newInstance(OkHttpClient client, SharedPreferences prefs) {
    return new TelegramApi(client, prefs);
  }
}
