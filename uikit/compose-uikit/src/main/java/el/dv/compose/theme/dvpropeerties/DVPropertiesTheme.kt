package el.dv.compose.theme.dvpropeerties

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import el.dv.compose.theme.DVSoftwareShapes

@Composable
fun DVPropertiesButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
    backgroundColor = DVPropertiesColor.primary,
    contentColor = DVPropertiesColor.primary,
    disabledBackgroundColor = DVPropertiesColor.secondaryContainer,
    disabledContentColor = DVPropertiesColor.secondaryContainer
)

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
fun dVPropertiesTextFieldColors(): TextFieldColors = TextFieldDefaults.textFieldColors(
    textColor = MaterialTheme.colors.onSurface,
    disabledTextColor = MaterialTheme.colors.secondaryVariant,
    backgroundColor = MaterialTheme.colors.surface,
    cursorColor = MaterialTheme.colors.primary,
    errorCursorColor = MaterialTheme.colors.error,
    focusedIndicatorColor = MaterialTheme.colors.primary,
    unfocusedIndicatorColor = MaterialTheme.colors.onBackground,
    disabledIndicatorColor = MaterialTheme.colors.onBackground,
    errorIndicatorColor = MaterialTheme.colors.error,
    errorLabelColor = MaterialTheme.colors.error,
    leadingIconColor = MaterialTheme.colors.primary,
    disabledLabelColor = MaterialTheme.colors.onBackground,
    errorLeadingIconColor = MaterialTheme.colors.error,
    trailingIconColor = Color.Green,
    disabledLeadingIconColor = MaterialTheme.colors.onBackground,
    disabledPlaceholderColor = MaterialTheme.colors.onBackground,
    disabledTrailingIconColor = MaterialTheme.colors.onBackground,
    errorTrailingIconColor = MaterialTheme.colors.error,
    focusedLabelColor = MaterialTheme.colors.primary,
    placeholderColor = MaterialTheme.colors.secondaryVariant,
    unfocusedLabelColor = MaterialTheme.colors.surface
)

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
