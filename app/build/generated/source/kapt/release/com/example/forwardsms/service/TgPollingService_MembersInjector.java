package com.example.forwardsms.service;

import com.example.forwardsms.data.dao.ForwardedSmsDao;
import com.example.forwardsms.data.dao.LogDao;
import com.example.forwardsms.telegram.TelegramApi;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class TgPollingService_MembersInjector implements MembersInjector<TgPollingService> {
  private final Provider<TelegramApi> telegramApiProvider;

  private final Provider<ForwardedSmsDao> forwardedDaoProvider;

  private final Provider<LogDao> logDaoProvider;

  public TgPollingService_MembersInjector(Provider<TelegramApi> telegramApiProvider,
      Provider<ForwardedSmsDao> forwardedDaoProvider, Provider<LogDao> logDaoProvider) {
    this.telegramApiProvider = telegramApiProvider;
    this.forwardedDaoProvider = forwardedDaoProvider;
    this.logDaoProvider = logDaoProvider;
  }

  public static MembersInjector<TgPollingService> create(Provider<TelegramApi> telegramApiProvider,
      Provider<ForwardedSmsDao> forwardedDaoProvider, Provider<LogDao> logDaoProvider) {
    return new TgPollingService_MembersInjector(telegramApiProvider, forwardedDaoProvider, logDaoProvider);
  }

  @Override
  public void injectMembers(TgPollingService instance) {
    injectTelegramApi(instance, telegramApiProvider.get());
    injectForwardedDao(instance, forwardedDaoProvider.get());
    injectLogDao(instance, logDaoProvider.get());
  }

  @InjectedFieldSignature("com.example.forwardsms.service.TgPollingService.telegramApi")
  public static void injectTelegramApi(TgPollingService instance, TelegramApi telegramApi) {
    instance.telegramApi = telegramApi;
  }

  @InjectedFieldSignature("com.example.forwardsms.service.TgPollingService.forwardedDao")
  public static void injectForwardedDao(TgPollingService instance, ForwardedSmsDao forwardedDao) {
    instance.forwardedDao = forwardedDao;
  }

  @InjectedFieldSignature("com.example.forwardsms.service.TgPollingService.logDao")
  public static void injectLogDao(TgPollingService instance, LogDao logDao) {
    instance.logDao = logDao;
  }
}
