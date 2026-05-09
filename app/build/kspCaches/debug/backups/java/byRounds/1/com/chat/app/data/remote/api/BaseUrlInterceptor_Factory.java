package com.chat.app.data.remote.api;

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
public final class BaseUrlInterceptor_Factory implements Factory<BaseUrlInterceptor> {
  private final Provider<ServerConfigDataStore> serverConfigProvider;

  public BaseUrlInterceptor_Factory(Provider<ServerConfigDataStore> serverConfigProvider) {
    this.serverConfigProvider = serverConfigProvider;
  }

  @Override
  public BaseUrlInterceptor get() {
    return newInstance(serverConfigProvider.get());
  }

  public static BaseUrlInterceptor_Factory create(
      Provider<ServerConfigDataStore> serverConfigProvider) {
    return new BaseUrlInterceptor_Factory(serverConfigProvider);
  }

  public static BaseUrlInterceptor newInstance(ServerConfigDataStore serverConfig) {
    return new BaseUrlInterceptor(serverConfig);
  }
}
