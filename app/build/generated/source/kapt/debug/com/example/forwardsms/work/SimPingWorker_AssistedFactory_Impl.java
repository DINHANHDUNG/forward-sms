package com.example.forwardsms.work;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class SimPingWorker_AssistedFactory_Impl implements SimPingWorker_AssistedFactory {
  private final SimPingWorker_Factory delegateFactory;

  SimPingWorker_AssistedFactory_Impl(SimPingWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public SimPingWorker create(Context context, WorkerParameters parameters) {
    return delegateFactory.get(context, parameters);
  }

  public static Provider<SimPingWorker_AssistedFactory> create(
      SimPingWorker_Factory delegateFactory) {
    return InstanceFactory.create(new SimPingWorker_AssistedFactory_Impl(delegateFactory));
  }
}
