package com.chat.app;

import com.chat.app.data.local.ThemeDataStore;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<ThemeDataStore> themeDataStoreProvider;

  public MainActivity_MembersInjector(Provider<ThemeDataStore> themeDataStoreProvider) {
    this.themeDataStoreProvider = themeDataStoreProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<ThemeDataStore> themeDataStoreProvider) {
    return new MainActivity_MembersInjector(themeDataStoreProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectThemeDataStore(instance, themeDataStoreProvider.get());
  }

  @InjectedFieldSignature("com.chat.app.MainActivity.themeDataStore")
  public static void injectThemeDataStore(MainActivity instance, ThemeDataStore themeDataStore) {
    instance.themeDataStore = themeDataStore;
  }
}
