package com.chat.app.data.remote.api

import com.chat.app.data.local.ServerConfigDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Rewrites the base URL of every request to use the host
 * currently stored in [ServerConfigDataStore].
 * This allows the user to change the server address at runtime
 * without recreating the Retrofit instance.
 */
class BaseUrlInterceptor @Inject constructor(
    private val serverConfig: ServerConfigDataStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val host = runBlocking { serverConfig.getHttpHost() }
        val original = chain.request()
        val newUrl = original.url
            .newBuilder()
            .host(host.substringBefore(":"))
            .port(host.substringAfter(":", "8080").toIntOrNull() ?: 8080)
            .build()

        val newRequest = original.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
