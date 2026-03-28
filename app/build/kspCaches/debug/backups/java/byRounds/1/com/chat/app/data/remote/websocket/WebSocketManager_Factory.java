package com.chat.app.data.remote.websocket;

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

  private final Provider<String> wsBaseUrlProvider;

  public WebSocketManager_Factory(Provider<OkHttpClient> okHttpClientProvider,
      Provider<Gson> gsonProvider, Provider<String> wsBaseUrlProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
    this.gsonProvider = gsonProvider;
    this.wsBaseUrlProvider = wsBaseUrlProvider;
  }

  @Override
  public WebSocketManager get() {
    return newInstance(okHttpClientProvider.get(), gsonProvider.get(), wsBaseUrlProvider.get());
  }

  public static WebSocketManager_Factory create(Provider<OkHttpClient> okHttpClientProvider,
      Provider<Gson> gsonProvider, Provider<String> wsBaseUrlProvider) {
    return new WebSocketManager_Factory(okHttpClientProvider, gsonProvider, wsBaseUrlProvider);
  }

  public static WebSocketManager newInstance(OkHttpClient okHttpClient, Gson gson,
      String wsBaseUrl) {
    return new WebSocketManager(okHttpClient, gson, wsBaseUrl);
  }
}
