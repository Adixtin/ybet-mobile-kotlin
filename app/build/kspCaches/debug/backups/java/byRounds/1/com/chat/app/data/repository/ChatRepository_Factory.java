package com.chat.app.data.repository;

import com.chat.app.data.local.TokenDataStore;
import com.chat.app.data.remote.api.ChatApiService;
import com.chat.app.data.remote.websocket.WebSocketManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class ChatRepository_Factory implements Factory<ChatRepository> {
  private final Provider<ChatApiService> apiServiceProvider;

  private final Provider<WebSocketManager> webSocketManagerProvider;

  private final Provider<TokenDataStore> tokenDataStoreProvider;

  public ChatRepository_Factory(Provider<ChatApiService> apiServiceProvider,
      Provider<WebSocketManager> webSocketManagerProvider,
      Provider<TokenDataStore> tokenDataStoreProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.webSocketManagerProvider = webSocketManagerProvider;
    this.tokenDataStoreProvider = tokenDataStoreProvider;
  }

  @Override
  public ChatRepository get() {
    return newInstance(apiServiceProvider.get(), webSocketManagerProvider.get(), tokenDataStoreProvider.get());
  }

  public static ChatRepository_Factory create(Provider<ChatApiService> apiServiceProvider,
      Provider<WebSocketManager> webSocketManagerProvider,
      Provider<TokenDataStore> tokenDataStoreProvider) {
    return new ChatRepository_Factory(apiServiceProvider, webSocketManagerProvider, tokenDataStoreProvider);
  }

  public static ChatRepository newInstance(ChatApiService apiService,
      WebSocketManager webSocketManager, TokenDataStore tokenDataStore) {
    return new ChatRepository(apiService, webSocketManager, tokenDataStore);
  }
}
