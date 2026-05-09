package com.chat.app.di;

import com.chat.app.data.remote.api.AuthInterceptor;
import com.chat.app.data.remote.api.BaseUrlInterceptor;
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
public final class AppModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final Provider<BaseUrlInterceptor> baseUrlInterceptorProvider;

  private final Provider<AuthInterceptor> authInterceptorProvider;

  private final Provider<HttpLoggingInterceptor> loggingInterceptorProvider;

  public AppModule_ProvideOkHttpClientFactory(
      Provider<BaseUrlInterceptor> baseUrlInterceptorProvider,
      Provider<AuthInterceptor> authInterceptorProvider,
      Provider<HttpLoggingInterceptor> loggingInterceptorProvider) {
    this.baseUrlInterceptorProvider = baseUrlInterceptorProvider;
    this.authInterceptorProvider = authInterceptorProvider;
    this.loggingInterceptorProvider = loggingInterceptorProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttpClient(baseUrlInterceptorProvider.get(), authInterceptorProvider.get(), loggingInterceptorProvider.get());
  }

  public static AppModule_ProvideOkHttpClientFactory create(
      Provider<BaseUrlInterceptor> baseUrlInterceptorProvider,
      Provider<AuthInterceptor> authInterceptorProvider,
      Provider<HttpLoggingInterceptor> loggingInterceptorProvider) {
    return new AppModule_ProvideOkHttpClientFactory(baseUrlInterceptorProvider, authInterceptorProvider, loggingInterceptorProvider);
  }

  public static OkHttpClient provideOkHttpClient(BaseUrlInterceptor baseUrlInterceptor,
      AuthInterceptor authInterceptor, HttpLoggingInterceptor loggingInterceptor) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideOkHttpClient(baseUrlInterceptor, authInterceptor, loggingInterceptor));
  }
}
