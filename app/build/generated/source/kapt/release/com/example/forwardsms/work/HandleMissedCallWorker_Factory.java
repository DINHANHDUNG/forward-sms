package com.example.forwardsms.work;

import android.content.Context;
import androidx.work.WorkerParameters;
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
public final class HandleMissedCallWorker_Factory {
  private final Provider<MessageRepository> repoProvider;

  public HandleMissedCallWorker_Factory(Provider<MessageRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  public HandleMissedCallWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, repoProvider.get());
  }

  public static HandleMissedCallWorker_Factory create(Provider<MessageRepository> repoProvider) {
    return new HandleMissedCallWorker_Factory(repoProvider);
  }

  public static HandleMissedCallWorker newInstance(Context appContext,
      WorkerParameters workerParams, MessageRepository repo) {
    return new HandleMissedCallWorker(appContext, workerParams, repo);
  }
}
