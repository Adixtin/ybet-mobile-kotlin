package com.chat.app.di;

import com.chat.app.data.remote.api.ChatApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class AppModule_ProvideChatApiServiceFactory implements Factory<ChatApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public AppModule_ProvideChatApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public ChatApiService get() {
    return provideChatApiService(retrofitProvider.get());
  }

  public static AppModule_ProvideChatApiServiceFactory create(Provider<Retrofit> retrofitProvider) {
    return new AppModule_ProvideChatApiServiceFactory(retrofitProvider);
  }

  public static ChatApiService provideChatApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideChatApiService(retrofit));
  }
}
