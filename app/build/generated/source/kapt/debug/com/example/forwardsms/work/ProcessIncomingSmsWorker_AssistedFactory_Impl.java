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
public final class ProcessIncomingSmsWorker_AssistedFactory_Impl implements ProcessIncomingSmsWorker_AssistedFactory {
  private final ProcessIncomingSmsWorker_Factory delegateFactory;

  ProcessIncomingSmsWorker_AssistedFactory_Impl(ProcessIncomingSmsWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public ProcessIncomingSmsWorker create(Context context, WorkerParameters parameters) {
    return delegateFactory.get(context, parameters);
  }

  public static Provider<ProcessIncomingSmsWorker_AssistedFactory> create(
      ProcessIncomingSmsWorker_Factory delegateFactory) {
    return InstanceFactory.create(new ProcessIncomingSmsWorker_AssistedFactory_Impl(delegateFactory));
  }
}
