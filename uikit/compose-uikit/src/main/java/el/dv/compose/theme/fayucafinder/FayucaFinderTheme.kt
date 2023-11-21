package el.dv.compose.theme.fayucafinder

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import el.dv.compose.theme.DVSoftwareShapes

private val FayucaFinderLightThemeColors = lightColors(
    primary = FayucaFinderColor.Palette.deepOrange500,
    primaryVariant = FayucaFinderColor.Palette.deepOrange500Light,
    secondary = FayucaFinderColor.Palette.deepOrange500,
    secondaryVariant = FayucaFinderColor.Palette.deepOrange500Light,

    background = FayucaFinderColor.Palette.white,
    surface = FayucaFinderColor.Palette.white,
    error = FayucaFinderColor.Palette.red,

    onPrimary = FayucaFinderColor.Palette.white,
    onSecondary = FayucaFinderColor.Palette.white,
    onBackground = FayucaFinderColor.Palette.grey500,
    onSurface = FayucaFinderColor.Palette.grey500,
    onError = FayucaFinderColor.Palette.red
)

private val FayucaFinderDarkThemeColors = FayucaFinderLightThemeColors

@Composable
fun FayucaFinderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) FayucaFinderDarkThemeColors else FayucaFinderLightThemeColors,
        typography = if (darkTheme) FayucaFinderDarkTypography else FayucaFinderLightTypography,
        shapes = DVSoftwareShapes,
        content = content
    )
}
