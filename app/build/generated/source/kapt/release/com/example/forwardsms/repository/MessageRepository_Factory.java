package com.example.forwardsms.repository;

import android.content.Context;
import com.example.forwardsms.data.dao.ForwardedSmsDao;
import com.example.forwardsms.data.dao.LogDao;
import com.example.forwardsms.data.dao.PendingMessageDao;
import com.example.forwardsms.telegram.TelegramApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MessageRepository_Factory implements Factory<MessageRepository> {
  private final Provider<TelegramApi> apiProvider;

  private final Provider<PendingMessageDao> pendingDaoProvider;

  private final Provider<LogDao> logDaoProvider;

  private final Provider<ForwardedSmsDao> forwardedDaoProvider;

  private final Provider<Context> contextProvider;

  public MessageRepository_Factory(Provider<TelegramApi> apiProvider,
      Provider<PendingMessageDao> pendingDaoProvider, Provider<LogDao> logDaoProvider,
      Provider<ForwardedSmsDao> forwardedDaoProvider, Provider<Context> contextProvider) {
    this.apiProvider = apiProvider;
    this.pendingDaoProvider = pendingDaoProvider;
    this.logDaoProvider = logDaoProvider;
    this.forwardedDaoProvider = forwardedDaoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public MessageRepository get() {
    return newInstance(apiProvider.get(), pendingDaoProvider.get(), logDaoProvider.get(), forwardedDaoProvider.get(), contextProvider.get());
  }

  public static MessageRepository_Factory create(Provider<TelegramApi> apiProvider,
      Provider<PendingMessageDao> pendingDaoProvider, Provider<LogDao> logDaoProvider,
      Provider<ForwardedSmsDao> forwardedDaoProvider, Provider<Context> contextProvider) {
    return new MessageRepository_Factory(apiProvider, pendingDaoProvider, logDaoProvider, forwardedDaoProvider, contextProvider);
  }

  public static MessageRepository newInstance(TelegramApi api, PendingMessageDao pendingDao,
      LogDao logDao, ForwardedSmsDao forwardedDao, Context context) {
    return new MessageRepository(api, pendingDao, logDao, forwardedDao, context);
  }
}
