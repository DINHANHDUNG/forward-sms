package com.example.forwardsms.di;

import android.content.SharedPreferences;
import com.example.forwardsms.telegram.TelegramApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideTelegramApiFactory implements Factory<TelegramApi> {
  private final Provider<OkHttpClient> clientProvider;

  private final Provider<SharedPreferences> prefsProvider;

  public AppModule_ProvideTelegramApiFactory(Provider<OkHttpClient> clientProvider,
      Provider<SharedPreferences> prefsProvider) {
    this.clientProvider = clientProvider;
    this.prefsProvider = prefsProvider;
  }

  @Override
  public TelegramApi get() {
    return provideTelegramApi(clientProvider.get(), prefsProvider.get());
  }

  public static AppModule_ProvideTelegramApiFactory create(Provider<OkHttpClient> clientProvider,
      Provider<SharedPreferences> prefsProvider) {
    return new AppModule_ProvideTelegramApiFactory(clientProvider, prefsProvider);
  }

  public static TelegramApi provideTelegramApi(OkHttpClient client, SharedPreferences prefs) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTelegramApi(client, prefs));
  }
}
