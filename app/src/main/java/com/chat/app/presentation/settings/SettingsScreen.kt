package com.chat.app.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    fontSizeMultiplier: Float,
    onFontSizeChange: (Float) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Font Size",
                style = MaterialTheme.typography.titleMedium
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Small", fontSize = 12.sp)
                Slider(
                    value = fontSizeMultiplier,
                    onValueChange = onFontSizeChange,
                    valueRange = 0.8f..1.5f,
                    modifier = Modifier.weight(1f)
                )
                Text("Large", fontSize = 20.sp)
            }
            
            Text(
                text = "Preview Text",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "This is how your chat messages will look. Adjust the slider above to change the size.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
