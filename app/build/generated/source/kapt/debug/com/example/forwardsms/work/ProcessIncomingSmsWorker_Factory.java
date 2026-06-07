package com.example.forwardsms.work;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.example.forwardsms.data.dao.ForwardedSmsDao;
import com.example.forwardsms.repository.MessageRepository;
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
public final class ProcessIncomingSmsWorker_Factory {
  private final Provider<MessageRepository> repoProvider;

  private final Provider<ForwardedSmsDao> forwardedDaoProvider;

  public ProcessIncomingSmsWorker_Factory(Provider<MessageRepository> repoProvider,
      Provider<ForwardedSmsDao> forwardedDaoProvider) {
    this.repoProvider = repoProvider;
    this.forwardedDaoProvider = forwardedDaoProvider;
  }

  public ProcessIncomingSmsWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, repoProvider.get(), forwardedDaoProvider.get());
  }

  public static ProcessIncomingSmsWorker_Factory create(Provider<MessageRepository> repoProvider,
      Provider<ForwardedSmsDao> forwardedDaoProvider) {
    return new ProcessIncomingSmsWorker_Factory(repoProvider, forwardedDaoProvider);
  }

  public static ProcessIncomingSmsWorker newInstance(Context appContext,
      WorkerParameters workerParams, MessageRepository repo, ForwardedSmsDao forwardedDao) {
    return new ProcessIncomingSmsWorker(appContext, workerParams, repo, forwardedDao);
  }
}
