package com.chat.app.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class AppModule_ProvideWsBaseUrlFactory implements Factory<String> {
  @Override
  public String get() {
    return provideWsBaseUrl();
  }

  public static AppModule_ProvideWsBaseUrlFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static String provideWsBaseUrl() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideWsBaseUrl());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideWsBaseUrlFactory INSTANCE = new AppModule_ProvideWsBaseUrlFactory();
  }
}
