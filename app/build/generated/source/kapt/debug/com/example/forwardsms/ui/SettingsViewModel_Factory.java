package com.example.forwardsms.ui;

import android.content.SharedPreferences;
import com.example.forwardsms.telegram.TelegramApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<SharedPreferences> prefsProvider;

  private final Provider<TelegramApi> telegramApiProvider;

  public SettingsViewModel_Factory(Provider<SharedPreferences> prefsProvider,
      Provider<TelegramApi> telegramApiProvider) {
    this.prefsProvider = prefsProvider;
    this.telegramApiProvider = telegramApiProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(prefsProvider.get(), telegramApiProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<SharedPreferences> prefsProvider,
      Provider<TelegramApi> telegramApiProvider) {
    return new SettingsViewModel_Factory(prefsProvider, telegramApiProvider);
  }

  public static SettingsViewModel newInstance(SharedPreferences prefs, TelegramApi telegramApi) {
    return new SettingsViewModel(prefs, telegramApi);
  }
}
