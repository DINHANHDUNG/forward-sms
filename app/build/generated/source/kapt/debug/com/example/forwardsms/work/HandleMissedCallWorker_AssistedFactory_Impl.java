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
public final class HandleMissedCallWorker_AssistedFactory_Impl implements HandleMissedCallWorker_AssistedFactory {
  private final HandleMissedCallWorker_Factory delegateFactory;

  HandleMissedCallWorker_AssistedFactory_Impl(HandleMissedCallWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public HandleMissedCallWorker create(Context context, WorkerParameters parameters) {
    return delegateFactory.get(context, parameters);
  }

  public static Provider<HandleMissedCallWorker_AssistedFactory> create(
      HandleMissedCallWorker_Factory delegateFactory) {
    return InstanceFactory.create(new HandleMissedCallWorker_AssistedFactory_Impl(delegateFactory));
  }
}
