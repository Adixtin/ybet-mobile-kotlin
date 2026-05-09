package com.chat.app.di

import com.chat.app.data.remote.api.AuthInterceptor
import com.chat.app.data.remote.api.BaseUrlInterceptor
import com.chat.app.data.remote.api.ChatApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val HTTP_BASE_URL = "http://10.0.2.2:8080/api/v1/"   // placeholder — host is rewritten by BaseUrlInterceptor

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        baseUrlInterceptor: BaseUrlInterceptor,
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(baseUrlInterceptor)   // must be first — rewrites host:port
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(0, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    /**
     * Separate client for WebSocket — NO BaseUrlInterceptor.
     * The WS URL is built from ServerConfigDataStore inside WebSocketManager,
     * so we must not let BaseUrlInterceptor overwrite the host.
     */
    @Provides
    @Named("wsOkHttp")
    @Singleton
    fun provideWsOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(0, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(HTTP_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideChatApiService(retrofit: Retrofit): ChatApiService =
        retrofit.create(ChatApiService::class.java)
}