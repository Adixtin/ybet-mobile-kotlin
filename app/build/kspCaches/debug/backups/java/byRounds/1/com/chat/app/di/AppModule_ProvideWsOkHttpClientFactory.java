package com.chat.app.di;

import com.chat.app.data.remote.api.AuthInterceptor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

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
public final class AppModule_ProvideWsOkHttpClientFactory implements Factory<OkHttpClient> {
  private final Provider<AuthInterceptor> authInterceptorProvider;

  private final Provider<HttpLoggingInterceptor> loggingInterceptorProvider;

  public AppModule_ProvideWsOkHttpClientFactory(Provider<AuthInterceptor> authInterceptorProvider,
      Provider<HttpLoggingInterceptor> loggingInterceptorProvider) {
    this.authInterceptorProvider = authInterceptorProvider;
    this.loggingInterceptorProvider = loggingInterceptorProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideWsOkHttpClient(authInterceptorProvider.get(), loggingInterceptorProvider.get());
  }

  public static AppModule_ProvideWsOkHttpClientFactory create(
      Provider<AuthInterceptor> authInterceptorProvider,
      Provider<HttpLoggingInterceptor> loggingInterceptorProvider) {
    return new AppModule_ProvideWsOkHttpClientFactory(authInterceptorProvider, loggingInterceptorProvider);
  }

  public static OkHttpClient provideWsOkHttpClient(AuthInterceptor authInterceptor,
      HttpLoggingInterceptor loggingInterceptor) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideWsOkHttpClient(authInterceptor, loggingInterceptor));
  }
}
