package el.dv.weatherapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = primary,
    primaryContainer = primaryContainer,
    secondary = secondary,
    secondaryContainer = secondaryContainer,
    tertiary = tertiary,
    tertiaryContainer = tertiaryContainer,
    error = error,
    errorContainer = errorContainer
)

private val LightColorScheme = lightColorScheme(
    primary = primary,
    primaryContainer = primaryContainer,
    secondary = secondary,
    secondaryContainer = secondaryContainer,
    tertiary = tertiary,
    tertiaryContainer = tertiaryContainer,
    error = error,
    errorContainer = errorContainer
)

@Composable
fun WeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
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
