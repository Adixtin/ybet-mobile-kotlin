package com.chat.app.data.local;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class ServerConfigDataStore_Factory implements Factory<ServerConfigDataStore> {
  private final Provider<Context> contextProvider;

  public ServerConfigDataStore_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ServerConfigDataStore get() {
    return newInstance(contextProvider.get());
  }

  public static ServerConfigDataStore_Factory create(Provider<Context> contextProvider) {
    return new ServerConfigDataStore_Factory(contextProvider);
  }

  public static ServerConfigDataStore newInstance(Context context) {
    return new ServerConfigDataStore(context);
  }
}
