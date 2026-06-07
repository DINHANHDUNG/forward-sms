package com.example.forwardsms.work;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.example.forwardsms.data.dao.PendingMessageDao;
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
public final class QueueWorker_Factory {
  private final Provider<PendingMessageDao> pendingDaoProvider;

  private final Provider<MessageRepository> repoProvider;

  public QueueWorker_Factory(Provider<PendingMessageDao> pendingDaoProvider,
      Provider<MessageRepository> repoProvider) {
    this.pendingDaoProvider = pendingDaoProvider;
    this.repoProvider = repoProvider;
  }

  public QueueWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, pendingDaoProvider.get(), repoProvider.get());
  }

  public static QueueWorker_Factory create(Provider<PendingMessageDao> pendingDaoProvider,
      Provider<MessageRepository> repoProvider) {
    return new QueueWorker_Factory(pendingDaoProvider, repoProvider);
  }

  public static QueueWorker newInstance(Context appContext, WorkerParameters workerParams,
      PendingMessageDao pendingDao, MessageRepository repo) {
    return new QueueWorker(appContext, workerParams, pendingDao, repo);
  }
}
