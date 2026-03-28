package com.chat.app.data.remote.websocket

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retryWhen
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

private const val TAG = "WebSocketManager"
private const val RECONNECT_DELAY_MS = 5_000L
private const val MAX_RECONNECT_ATTEMPTS = 10L

@Singleton
class WebSocketManager @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson,
    @Named("wsBaseUrl") private val wsBaseUrl: String
) {
    private var webSocket: WebSocket? = null

    fun observe(token: String): Flow<WsEvent> = rawFlow(token)
        .retryWhen { cause, attempt ->
            Log.w(TAG, "WS error (attempt $attempt): ${cause::class.simpleName} — ${cause.message}")
            if (attempt >= MAX_RECONNECT_ATTEMPTS) {
                Log.e(TAG, "Max reconnect attempts reached, giving up")
                return@retryWhen false
            }
            val reconnectable = cause is SocketTimeoutException
                    || cause is java.io.IOException
                    || cause is java.net.ConnectException
            if (reconnectable) {
                delay(RECONNECT_DELAY_MS)
                true
            } else {
                false
            }
        }
        .catch { cause ->
            Log.e(TAG, "Unrecoverable WS error", cause)
            emit(WsEvent.Error(cause))
        }

    private fun rawFlow(token: String): Flow<WsEvent> = callbackFlow {
        val request = Request.Builder()
            .url("$wsBaseUrl/ws")
            .addHeader("Authorization", "Bearer $token")
            .build()

        val listener = object : WebSocketListener() {
            override fun onOpen(ws: WebSocket, response: Response) {
                Log.d(TAG, "Connected")
                trySend(WsEvent.Connected)
            }

            override fun onMessage(ws: WebSocket, text: String) {
                Log.d(TAG, "Message: $text")
                try {
                    val json: JsonObject = JsonParser.parseString(text).asJsonObject
                    val type = json.get("type").asString
                    val payload = json.get("payload").asJsonObject
                    val event = when (type) {
                        "userMessage"    -> WsEvent.NewMessage(gson.fromJson(payload, WsUserMessage::class.java))
                        "editMessage"    -> WsEvent.EditedMessage(gson.fromJson(payload, WsEditMessage::class.java))
                        "deleteMessage"  -> WsEvent.DeletedMessage(gson.fromJson(payload, WsDeleteMessage::class.java))
                        "systemMessage"  -> WsEvent.SystemMessage(gson.fromJson(payload, WsSystemMessage::class.java))
                        "userListUpdate" -> WsEvent.UserListUpdate(gson.fromJson(payload, WsUserListUpdate::class.java))
                        else -> null
                    }
                    event?.let { trySend(it) }
                } catch (e: Exception) {
                    Log.e(TAG, "Parse error", e)
                }
            }

            override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                Log.e(TAG, "onFailure: ${t::class.simpleName} — ${t.message}")
                close(t)
            }

            override fun onClosed(ws: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "Closed: $code $reason")
                trySend(WsEvent.Disconnected)
                close()
            }
        }

        webSocket = okHttpClient.newWebSocket(request, listener)
        Log.d(TAG, "WebSocket connecting to $wsBaseUrl/ws")

        awaitClose {
            Log.d(TAG, "Flow closing — shutting down socket")
            webSocket?.close(1000, "Flow closed")
            webSocket = null
        }
    }

    fun disconnect() {
        webSocket?.close(1000, "User disconnected")
        webSocket = null
    }
}