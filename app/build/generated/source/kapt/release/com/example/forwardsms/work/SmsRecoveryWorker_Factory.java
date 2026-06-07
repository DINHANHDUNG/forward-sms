package com.example.forwardsms.work;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.example.forwardsms.data.dao.ForwardedSmsDao;
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
public final class SmsRecoveryWorker_Factory {
  private final Provider<ForwardedSmsDao> forwardedDaoProvider;

  public SmsRecoveryWorker_Factory(Provider<ForwardedSmsDao> forwardedDaoProvider) {
    this.forwardedDaoProvider = forwardedDaoProvider;
  }

  public SmsRecoveryWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, forwardedDaoProvider.get());
  }

  public static SmsRecoveryWorker_Factory create(Provider<ForwardedSmsDao> forwardedDaoProvider) {
    return new SmsRecoveryWorker_Factory(forwardedDaoProvider);
  }

  public static SmsRecoveryWorker newInstance(Context appContext, WorkerParameters workerParams,
      ForwardedSmsDao forwardedDao) {
    return new SmsRecoveryWorker(appContext, workerParams, forwardedDao);
  }
}
