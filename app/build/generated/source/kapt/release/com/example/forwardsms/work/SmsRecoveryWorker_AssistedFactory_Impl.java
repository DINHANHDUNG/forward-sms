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
public final class SmsRecoveryWorker_AssistedFactory_Impl implements SmsRecoveryWorker_AssistedFactory {
  private final SmsRecoveryWorker_Factory delegateFactory;

  SmsRecoveryWorker_AssistedFactory_Impl(SmsRecoveryWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public SmsRecoveryWorker create(Context context, WorkerParameters parameters) {
    return delegateFactory.get(context, parameters);
  }

  public static Provider<SmsRecoveryWorker_AssistedFactory> create(
      SmsRecoveryWorker_Factory delegateFactory) {
    return InstanceFactory.create(new SmsRecoveryWorker_AssistedFactory_Impl(delegateFactory));
  }
}
