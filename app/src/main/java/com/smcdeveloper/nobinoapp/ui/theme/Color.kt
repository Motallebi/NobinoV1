package com.smcdeveloper.nobinoapp.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color






// 🎨 Define custom light mode colors
val primaryLight = Color(0xFFFFC107)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFC5062D)
val onPrimaryContainerLight = Color(0xFF21005D)
val secondaryLight = Color(0xFF009688)
val onSecondaryLight = Color(0xFFFFFFFF)
val backgroundLight = Color(0xFFFFFBFE)
val onBackgroundLight = Color(0xFF1C1B1F)





// 🎨 Define custom dark mode colors
val primaryDark = Color(0xFF1A1A1A)
val onPrimaryDark = Color(0xFF381E72)
val primaryContainerDark = Color(0xFF4F378B)
val onPrimaryContainerDark = Color(0xFFEADDFF)
val secondaryDark = Color(0xFFCCC2DC)
val onSecondaryDark = Color(0xFF332D41)
val backgroundDark = Color(0xFF1C1B1F)
val onBackgroundDark = Color(0xFFE6E1E5)











val ColorScheme.selectedBottomBar: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF43474C) else Color(0xFFCFD4DA)

val ColorScheme.unSelectedBottomBar: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFA4A1A1) else Color(0xFF575A5E)


val ColorScheme.bottomBar: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFFFFFFF) else Color(0xFF303235)

















