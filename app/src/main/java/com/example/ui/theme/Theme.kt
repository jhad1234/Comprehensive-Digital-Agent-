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

private val DarkColorScheme = darkColorScheme(
    primary = BoldPrimaryDark,
    onPrimary = BoldOnPrimaryDark,
    primaryContainer = BoldPrimaryContainerDark,
    onPrimaryContainer = BoldOnPrimaryContainerDark,
    secondary = BoldSecondaryDark,
    onSecondary = BoldOnSecondaryDark,
    secondaryContainer = BoldSecondaryContainerDark,
    onSecondaryContainer = BoldOnSecondaryContainerDark,
    tertiary = BoldTertiaryDark,
    onTertiary = BoldOnTertiaryDark,
    tertiaryContainer = BoldTertiaryContainerDark,
    onTertiaryContainer = BoldOnTertiaryContainerDark,
    background = BoldBackgroundDark,
    onBackground = BoldOnBackgroundDark,
    surface = BoldSurfaceDark,
    onSurface = BoldOnSurfaceDark,
    surfaceVariant = BoldSurfaceVariantDark,
    onSurfaceVariant = BoldOnSurfaceVariantDark,
    outline = BoldOutlineDark,
    error = RoseError
)

private val LightColorScheme = lightColorScheme(
    primary = BoldPrimaryLight,
    onPrimary = BoldOnPrimaryLight,
    primaryContainer = BoldPrimaryContainerLight,
    onPrimaryContainer = BoldOnPrimaryContainerLight,
    secondary = BoldSecondaryLight,
    onSecondary = BoldOnSecondaryLight,
    secondaryContainer = BoldSecondaryContainerLight,
    onSecondaryContainer = BoldOnSecondaryContainerLight,
    tertiary = BoldTertiaryLight,
    onTertiary = BoldOnTertiaryLight,
    tertiaryContainer = BoldTertiaryContainerLight,
    onTertiaryContainer = BoldOnTertiaryContainerLight,
    background = BoldBackgroundLight,
    onBackground = BoldOnBackgroundLight,
    surface = BoldSurfaceLight,
    onSurface = BoldOnSurfaceLight,
    surfaceVariant = BoldSurfaceVariantLight,
    onSurfaceVariant = BoldOnSurfaceVariantLight,
    outline = BoldOutlineLight,
    error = RoseError
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Disabled dynamic color to preserve custom aesthetic
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

