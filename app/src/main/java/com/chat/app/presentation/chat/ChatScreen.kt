package com.chat.app.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chat.app.presentation.components.EditMessageBar
import com.chat.app.presentation.components.MessageBubble
import com.chat.app.presentation.components.MessageInputBar
import com.chat.app.presentation.components.OnlineUsersSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    onLogout: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var showUsersSheet by remember { mutableStateOf(false) }

    // Auto-scroll to bottom when new message arrives
    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    // Some info appearing as a popup
    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let {
            snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Chat", fontWeight = FontWeight.Bold)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            val dotColor = if (state.isConnected) Color(0xFF4CAF50) else Color(0xFFE53935)
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .drawBehind {
                                        drawCircle(dotColor, radius = size.minDimension / 2, center = Offset(size.width / 2, size.height / 2))
                                    }
                            )
                            Text(
                                if (state.isConnected) "Connected" else "Disconnected",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = viewModel::loadHistory) {
                        Icon(Icons.Default.Refresh, "Refresh")
                    }
                    IconButton(onClick = { showUsersSheet = true }) {
                        BadgedBox(
                            badge = {
                                if (state.onlineUsers.isNotEmpty()) {
                                    Badge { Text("${state.onlineUsers.size}") }
                                }
                            }
                        ) {
                            Icon(Icons.Default.People, "Online users")
                        }
                    }
                    IconButton(onClick = {
                        viewModel.logout()
                        onLogout()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, "Logout")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Column {
                if (state.editingMessageId != null) {
                    EditMessageBar(
                        text = state.editInputText,
                        onTextChange = viewModel::onEditInputChange,
                        onConfirm = viewModel::confirmEdit,
                        onCancel = viewModel::cancelEdit
                    )
                } else {
                    MessageInputBar(
                        text = state.inputText,
                        onTextChange = viewModel::onInputChange,
                        onSend = viewModel::sendMessage,
                        isSending = state.isSending
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.isLoadingHistory) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.messages.isEmpty()) {
                Text(
                    "No messages yet. Say hello!",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(state.messages, key = { it.messageId }) { message ->
                        val isOwn = message.username == state.currentUsername
                        MessageBubble(
                            message = message,
                            isOwn = isOwn,
                            onEditClick = { viewModel.startEdit(message) },
                            onDeleteClick = { viewModel.deleteMessage(message.messageId) }
                        )
                    }
                }
            }
        }
    }

    if (showUsersSheet) {
        ModalBottomSheet(onDismissRequest = { showUsersSheet = false }) {
            OnlineUsersSheet(users = state.onlineUsers)
        }
    }
}