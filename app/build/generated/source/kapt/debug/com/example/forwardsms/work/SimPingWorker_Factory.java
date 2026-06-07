package com.example.forwardsms.work;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.work.WorkerParameters;
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
public final class SimPingWorker_Factory {
  private final Provider<SharedPreferences> prefsProvider;

  public SimPingWorker_Factory(Provider<SharedPreferences> prefsProvider) {
    this.prefsProvider = prefsProvider;
  }

  public SimPingWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, prefsProvider.get());
  }

  public static SimPingWorker_Factory create(Provider<SharedPreferences> prefsProvider) {
    return new SimPingWorker_Factory(prefsProvider);
  }

  public static SimPingWorker newInstance(Context appContext, WorkerParameters workerParams,
      SharedPreferences prefs) {
    return new SimPingWorker(appContext, workerParams, prefs);
  }
}
