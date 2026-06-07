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
public final class HeartbeatWorker_AssistedFactory_Impl implements HeartbeatWorker_AssistedFactory {
  private final HeartbeatWorker_Factory delegateFactory;

  HeartbeatWorker_AssistedFactory_Impl(HeartbeatWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public HeartbeatWorker create(Context context, WorkerParameters parameters) {
    return delegateFactory.get(context, parameters);
  }

  public static Provider<HeartbeatWorker_AssistedFactory> create(
      HeartbeatWorker_Factory delegateFactory) {
    return InstanceFactory.create(new HeartbeatWorker_AssistedFactory_Impl(delegateFactory));
  }
}
