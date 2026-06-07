package com.example.forwardsms.di;

import com.example.forwardsms.data.dao.ForwardedSmsDao;
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
public final class AppModule_ProvideForwardedSmsDaoFactory implements Factory<ForwardedSmsDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideForwardedSmsDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ForwardedSmsDao get() {
    return provideForwardedSmsDao(dbProvider.get());
  }

  public static AppModule_ProvideForwardedSmsDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideForwardedSmsDaoFactory(dbProvider);
  }

  public static ForwardedSmsDao provideForwardedSmsDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideForwardedSmsDao(db));
  }
}
