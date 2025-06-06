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
val onPrimaryLight = Color(0xFF2C3754)
val primaryContainerLight = Color(0xFFC5062D)
val onPrimaryContainerLight = Color(0xFF21005D)
val secondaryLight = Color(0xFF009688)
val onSecondaryLight = Color(0xFFFFFFFF)
val backgroundLight = Color(0xFFFFFBFE)
val onBackgroundLight = Color(0xFF4CAF50)





// 🎨 Define custom dark mode colors
val primaryDark = Color(0xFF0C0C0C)
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


val ColorScheme.kidsPageColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFD1C5A1) else Color(0xFFD1C5A1)


val ColorScheme.divider: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF212121) else Color(0xFF212121)



val ColorScheme.sliderdots: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF343434) else Color(0xFF343434)




val ColorScheme.disabledButtonColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFE21622) else Color(0xFFE21622)

val ColorScheme.ageSelectedButton: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF232328) else Color(0xFF232328)


val ColorScheme.searchIndicatorLine: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0x63636333) else Color(0x63636333)





















