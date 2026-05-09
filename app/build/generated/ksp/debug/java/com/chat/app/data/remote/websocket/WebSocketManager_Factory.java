package com.chat.app.data.remote.websocket;

import com.chat.app.data.local.ServerConfigDataStore;
import com.google.gson.Gson;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("javax.inject.Named")
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
public final class WebSocketManager_Factory implements Factory<WebSocketManager> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  private final Provider<Gson> gsonProvider;

  private final Provider<ServerConfigDataStore> serverConfigProvider;

  public WebSocketManager_Factory(Provider<OkHttpClient> okHttpClientProvider,
      Provider<Gson> gsonProvider, Provider<ServerConfigDataStore> serverConfigProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
    this.gsonProvider = gsonProvider;
    this.serverConfigProvider = serverConfigProvider;
  }

  @Override
  public WebSocketManager get() {
    return newInstance(okHttpClientProvider.get(), gsonProvider.get(), serverConfigProvider.get());
  }

  public static WebSocketManager_Factory create(Provider<OkHttpClient> okHttpClientProvider,
      Provider<Gson> gsonProvider, Provider<ServerConfigDataStore> serverConfigProvider) {
    return new WebSocketManager_Factory(okHttpClientProvider, gsonProvider, serverConfigProvider);
  }

  public static WebSocketManager newInstance(OkHttpClient okHttpClient, Gson gson,
      ServerConfigDataStore serverConfig) {
    return new WebSocketManager(okHttpClient, gson, serverConfig);
  }
}
