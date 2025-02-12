package com.smcdeveloper.nobinoapp.ui.theme
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val lightScheme = lightColorScheme(

    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
















)

private val darkScheme = darkColorScheme(


    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,






)



private val mediumContrastLightColorScheme = lightColorScheme()


private val highContrastLightColorScheme = lightColorScheme(

)

private val mediumContrastDarkColorScheme = darkColorScheme(

)

private val highContrastDarkColorScheme = darkColorScheme(

)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AppTheme(
    darkTheme: Boolean =
        !isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
   // dynamicColor: Boolean = true,
    content: @Composable() () -> Unit



)

{
  val colorScheme = when {

      
      darkTheme -> darkScheme
      else -> lightScheme
  }


    val nobinoShapes = Shapes(
        small = RoundedCornerShape(8.dp),  // Used by small buttons
        medium = RoundedCornerShape(16.dp), // Default shape for buttons
        large = RoundedCornerShape(24.dp),  // Used for large components
      )  // Used for large components


















  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.statusBarColor = colorScheme.primary.toArgb()
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content,
     shapes = nobinoShapes

  )
}

