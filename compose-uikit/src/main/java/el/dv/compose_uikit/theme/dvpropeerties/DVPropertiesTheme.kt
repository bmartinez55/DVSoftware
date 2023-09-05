package el.dv.compose_uikit.theme.dvpropeerties

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import el.dv.compose_uikit.theme.DVSoftwareShapes

private val DVPropertiesThemeColors = lightColors(
    primary = DVPropertiesColor.primary,
    primaryVariant = DVPropertiesColor.primaryContainer,
    secondary = DVPropertiesColor.secondary,
    secondaryVariant = DVPropertiesColor.secondaryContainer,

    background = DVPropertiesColor.background,
    surface = DVPropertiesColor.surface,
    error = DVPropertiesColor.error,

    onPrimary = DVPropertiesColor.onPrimary,
    onSecondary = DVPropertiesColor.onSecondary,
    onBackground = DVPropertiesColor.onBackground,
    onSurface = DVPropertiesColor.onSurface,
    onError = DVPropertiesColor.onError
)

private val DVPropertiesDarkThemeColors = DVPropertiesThemeColors

@Composable
fun DVPropertiesTheme(
    darkThem: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkThem) DVPropertiesDarkThemeColors else DVPropertiesThemeColors,
        typography = if (darkThem) DVPropertiesDarkTypography else DVPropertiesLightTypography,
        shapes = DVSoftwareShapes,
        content = content
    )
}
