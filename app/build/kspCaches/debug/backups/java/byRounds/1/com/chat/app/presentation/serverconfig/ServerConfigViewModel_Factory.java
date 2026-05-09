package com.chat.app.presentation.serverconfig;

import com.chat.app.data.local.ServerConfigDataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class ServerConfigViewModel_Factory implements Factory<ServerConfigViewModel> {
  private final Provider<ServerConfigDataStore> serverConfigProvider;

  public ServerConfigViewModel_Factory(Provider<ServerConfigDataStore> serverConfigProvider) {
    this.serverConfigProvider = serverConfigProvider;
  }

  @Override
  public ServerConfigViewModel get() {
    return newInstance(serverConfigProvider.get());
  }

  public static ServerConfigViewModel_Factory create(
      Provider<ServerConfigDataStore> serverConfigProvider) {
    return new ServerConfigViewModel_Factory(serverConfigProvider);
  }

  public static ServerConfigViewModel newInstance(ServerConfigDataStore serverConfig) {
    return new ServerConfigViewModel(serverConfig);
  }
}
