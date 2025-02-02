package com.smcdeveloper.nobinoapp.ui.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomBottomSheet(


    isVisible: Boolean,
    onDismiss: () -> Unit,
    sheetHeight: Dp = 400.dp, // Default height
    navigationBarHeight: Dp = 80.dp, // Adjust based on bottom navigation bar height
    content: @Composable ColumnScope.() -> Unit
) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // 🔴 Dims background when sheet is open
                .clickable(onClick = onDismiss), // 🔴 Click outside to dismiss
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sheetHeight)
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .pointerInput(Unit) { // 🔴 Prevent clicks from passing through the bottom sheet
                        detectTapGestures { /* Consume Clicks Inside the Sheet */ }
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    content() // 🔴 Content inside the bottom sheet
                }
            }
        }
    }
}
