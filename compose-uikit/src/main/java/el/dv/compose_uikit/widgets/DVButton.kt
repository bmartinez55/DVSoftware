package el.dv.compose_uikit.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import el.dv.compose_uikit.R
import el.dv.compose_uikit.theme.Dimens
import el.dv.compose_uikit.theme.fayucafinder.FayucaFinderColor
import el.dv.compose_uikit.theme.fayucafinder.FayucaFinderTheme
import el.dv.compose_uikit.theme.paddingEndSmall
import el.dv.compose_uikit.theme.paddingSmall

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    elevation: ButtonElevation = ButtonDefaults.elevation(
        defaultElevation = Dimens.Elevation.medium,
        pressedElevation = Dimens.Elevation.medium,
        disabledElevation = Dimens.Elevation.none
    ),
    backgroundColor: ButtonColors = primaryButtonColors(),
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
    backgroundColor: ButtonColors = primaryButtonColors(),
    shape: Shape = RoundedCornerShape(Dimens.CornerRadius.xlarge),
    textColor: Color = Color.White,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        elevation = elevation,
        enabled = enabled,
        colors = backgroundColor,
        shape = shape,
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
fun RoundedWhiteFilledButton(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    elevation: ButtonElevation = ButtonDefaults.elevation(
        defaultElevation = Dimens.Elevation.medium,
        pressedElevation = Dimens.Elevation.medium,
        disabledElevation = Dimens.Elevation.none
    ),
    backgroundColor: ButtonColors = whiteButtonColors(),
    shape: Shape = RoundedCornerShape(Dimens.CornerRadius.xlarge),
    textColor: Color = FayucaFinderColor.Palette.black,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        elevation = elevation,
        enabled = enabled,
        colors = backgroundColor,
        shape = shape,
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
fun RoundedIconWhiteFilledButton(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    fontSize: TextUnit = MaterialTheme.typography.body1.fontSize,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    elevation: ButtonElevation = ButtonDefaults.elevation(
        defaultElevation = Dimens.Elevation.medium,
        pressedElevation = Dimens.Elevation.medium,
        disabledElevation = Dimens.Elevation.none
    ),
    backgroundColor: ButtonColors = whiteButtonColors(),
    shape: Shape = RoundedCornerShape(Dimens.CornerRadius.xlarge),
    textColor: Color = FayucaFinderColor.Palette.black,
    enabled: Boolean = true,
    icon: Int? = null,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.then(modifier).requiredHeight(40.dp),
        elevation = elevation,
        enabled = enabled,
        colors = backgroundColor,
        shape = shape,
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            color = textColor,
            modifier = Modifier.paddingEndSmall(),
            fontWeight = fontWeight,
            letterSpacing = letterSpacing
        )

        if (icon != null) {
            Image(imageVector = ImageVector.vectorResource(icon), contentDescription = null)
        }
    }
}

@Composable
private fun primaryButtonColors() = ButtonDefaults.buttonColors(
    backgroundColor = when (isSystemInDarkTheme()) {
        true -> FayucaFinderColor.Palette.deepOrange500Light
        false -> FayucaFinderColor.Palette.deepOrange500
    },
    contentColor = FayucaFinderColor.Palette.white,
    disabledBackgroundColor = FayucaFinderColor.Palette.deepOrange500Dark.copy(alpha = 0.4f),
    disabledContentColor = FayucaFinderColor.Palette.white
)

@Composable
private fun whiteButtonColors() = ButtonDefaults.buttonColors(
    backgroundColor = FayucaFinderColor.Palette.white,
    contentColor = FayucaFinderColor.Palette.black,
    disabledBackgroundColor = FayucaFinderColor.Palette.grey500,
    disabledContentColor = FayucaFinderColor.Palette.white
)

@Preview()
@Composable
fun FilledButtonPreview() {
    FayucaFinderTheme {
        PrimaryButton(text = "TestingFilled") {}
    }
}

@Preview()
@Composable
fun RoundedFilledButtonPreview() {
    FayucaFinderTheme {
        RoundedFilledButton(text = "Testing Rounded") {}
    }
}

@Preview()
@Composable
fun RoundedWhiteFilledButtonPreview() {
    FayucaFinderTheme {
        RoundedWhiteFilledButton(text = "Testing White Rounded") {}
    }
}

@Preview()
@Composable
fun RoundedIconWhiteFilledButtonPreview() {
    FayucaFinderTheme {
        RoundedIconWhiteFilledButton(text = "Testing White Rounded", icon = R.drawable.ic_google_icon) {}
    }
}
