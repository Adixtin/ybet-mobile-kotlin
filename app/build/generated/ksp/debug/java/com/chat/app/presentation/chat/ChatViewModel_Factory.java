package com.chat.app.presentation.chat;

import com.chat.app.data.repository.ChatRepository;
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
public final class ChatViewModel_Factory implements Factory<ChatViewModel> {
  private final Provider<ChatRepository> repositoryProvider;

  public ChatViewModel_Factory(Provider<ChatRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ChatViewModel_Factory create(Provider<ChatRepository> repositoryProvider) {
    return new ChatViewModel_Factory(repositoryProvider);
  }

  public static ChatViewModel newInstance(ChatRepository repository) {
    return new ChatViewModel(repository);
  }
}
