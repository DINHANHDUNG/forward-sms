package com.example.forwardsms.work;

import androidx.hilt.work.WorkerAssistedFactory;
import androidx.work.ListenableWorker;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.annotation.processing.Generated;

@Generated("androidx.hilt.AndroidXHiltProcessor")
@Module
@InstallIn(SingletonComponent.class)
@OriginatingElement(
    topLevelClass = QueueWorker.class
)
public interface QueueWorker_HiltModule {
  @Binds
  @IntoMap
  @StringKey("com.example.forwardsms.work.QueueWorker")
  WorkerAssistedFactory<? extends ListenableWorker> bind(QueueWorker_AssistedFactory factory);
}
