package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
    darkColorScheme(
        primary = BlueAccent,
        onPrimary = Color.White,
        primaryContainer = NavyPrimary,
        onPrimaryContainer = Color.White,
        secondary = AmberAccent,
        onSecondary = Color.White,
        tertiary = SoftTeal,
        background = SurfaceDark,
        onBackground = TextLight,
        surface = CardDark,
        onSurface = TextLight,
        surfaceVariant = BorderDark,
        onSurfaceVariant = TextMutedDark,
        error = ErrorRed,
        onError = Color.White
    )

private val LightColorScheme =
    lightColorScheme(
        primary = NavyPrimary,
        onPrimary = Color.White,
        primaryContainer = Color(0xFFDBEAFE),
        onPrimaryContainer = NavyPrimary,
        secondary = BlueAccent,
        onSecondary = Color.White,
        tertiary = SoftTeal,
        background = SurfaceLight,
        onBackground = TextDark,
        surface = CardLight,
        onSurface = TextDark,
        surfaceVariant = Color(0xFFF1F5F9),
        onSurfaceVariant = TextMuted,
        error = ErrorRed,
        onError = Color.White
    )

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep branded palette for academic clarity
    content: @Composable () -> Unit,
) {
  val colorScheme =
      when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
      }

  MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
  )
}
