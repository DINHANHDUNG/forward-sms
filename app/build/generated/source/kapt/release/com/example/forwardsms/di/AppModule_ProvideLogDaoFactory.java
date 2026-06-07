package com.example.forwardsms.di;

import com.example.forwardsms.data.dao.LogDao;
import com.example.forwardsms.data.db.AppDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideLogDaoFactory implements Factory<LogDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideLogDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public LogDao get() {
    return provideLogDao(dbProvider.get());
  }

  public static AppModule_ProvideLogDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideLogDaoFactory(dbProvider);
  }

  public static LogDao provideLogDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideLogDao(db));
  }
}
