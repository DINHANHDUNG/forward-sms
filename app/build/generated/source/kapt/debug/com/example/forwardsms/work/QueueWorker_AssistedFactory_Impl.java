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
public final class QueueWorker_AssistedFactory_Impl implements QueueWorker_AssistedFactory {
  private final QueueWorker_Factory delegateFactory;

  QueueWorker_AssistedFactory_Impl(QueueWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public QueueWorker create(Context context, WorkerParameters parameters) {
    return delegateFactory.get(context, parameters);
  }

  public static Provider<QueueWorker_AssistedFactory> create(QueueWorker_Factory delegateFactory) {
    return InstanceFactory.create(new QueueWorker_AssistedFactory_Impl(delegateFactory));
  }
}
