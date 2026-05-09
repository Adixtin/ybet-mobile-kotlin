package com.chat.app.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    fontSizeMultiplier: Float,
    onFontSizeChange: (Float) -> Unit,
    accentColor: Color,
    onAccentColorChange: (Color) -> Unit,
    isRoundedBubbles: Boolean,
    onRoundedBubblesChange: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    val accentColors = listOf(
        Color(0xFF2D5A8A), // Default Blue
        Color(0xFF4CAF50), // Green
        Color(0xFFE91E63), // Pink
        Color(0xFF9C27B0), // Purple
        Color(0xFFFF9800), // Orange
        Color(0xFF00BCD4)  // Cyan
    )

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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Font Size Section
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
            }

            // Accent Color Section
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Accent Color",
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    accentColors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    width = if (accentColor.toArgb() == color.toArgb()) 3.dp else 0.dp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    shape = CircleShape
                                )
                                .clickable { onAccentColorChange(color) }
                        )
                    }
                }
            }

            // Bubble Style Section
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Message Bubble Style",
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(if (isRoundedBubbles) "Rounded Corners" else "Sharp Corners")
                    Switch(
                        checked = isRoundedBubbles,
                        onCheckedChange = onRoundedBubblesChange
                    )
                }
            }

            // Preview Section
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Preview",
                    style = MaterialTheme.typography.titleMedium
                )
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = if (isRoundedBubbles) MaterialTheme.shapes.medium else androidx.compose.ui.graphics.RectangleShape,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "This is a preview of your message style and font size.",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
