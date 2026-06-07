package com.example.forwardsms.di;

import com.example.forwardsms.data.dao.PendingMessageDao;
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
public final class AppModule_ProvidePendingMessageDaoFactory implements Factory<PendingMessageDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvidePendingMessageDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public PendingMessageDao get() {
    return providePendingMessageDao(dbProvider.get());
  }

  public static AppModule_ProvidePendingMessageDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvidePendingMessageDaoFactory(dbProvider);
  }

  public static PendingMessageDao providePendingMessageDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providePendingMessageDao(db));
  }
}
