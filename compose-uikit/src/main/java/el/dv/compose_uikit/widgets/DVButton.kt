package el.dv.compose_uikit.widgets

import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import el.dv.compose_uikit.theme.Dimens
import el.dv.compose_uikit.theme.FayucaFinderColor
import el.dv.compose_uikit.theme.paddingSmall

@Composable
fun FilledButton(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    elevation: ButtonElevation = ButtonDefaults.elevation(
        defaultElevation = Dimens.Elevation.medium,
        pressedElevation = Dimens.Elevation.medium,
        disabledElevation = Dimens.Elevation.none
    ),
    backgroundColor: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = FayucaFinderColor.Palette.deepOrange500
    ),
    textColor: Color = Color.White,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        elevation = elevation,
        enabled = enabled,
        colors = backgroundColor,
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            color = textColor,
            modifier = Modifier.paddingSmall(),
            fontWeight = fontWeight,
            letterSpacing = letterSpacing
        )
    }
}

@Composable
fun RoundedFilledButton(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    elevation: ButtonElevation = ButtonDefaults.elevation(
        defaultElevation = Dimens.Elevation.medium,
        pressedElevation = Dimens.Elevation.medium,
        disabledElevation = Dimens.Elevation.none
    ),
    backgroundColor: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = FayucaFinderColor.Palette.deepOrange500
    ),
    textColor: Color = Color.White,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        elevation = elevation,
        enabled = enabled,
        colors = backgroundColor,
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            color = textColor,
            modifier = Modifier.paddingSmall(),
            fontWeight = fontWeight,
            letterSpacing = letterSpacing
        )
    }
}

@Preview()
@Composable
fun FilledButtonPreview() {
    FilledButton(text = "Testing") {}
}
