package el.dv.compose.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import el.dv.compose.theme.dvpropeerties.DVPropertiesTheme

@Composable
fun FilledRoundedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.large,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) = Button(
    onClick = onClick,
    modifier = Modifier
        .height(45.dp)
        .then(modifier),
    enabled = enabled,
    interactionSource = interactionSource,
    elevation = elevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    content = content
)

@Composable
fun OutlinedRoundedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.large,
    border: BorderStroke = BorderStroke(1.dp, MaterialTheme.colors.primary),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) = OutlinedButton(
    onClick = { onClick.invoke() },
    modifier = Modifier
        .height(45.dp)
        .then(modifier),
    enabled = enabled,
    interactionSource = interactionSource,
    elevation = elevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    content = content
)

@Preview(
    name = "FilledRoundedButtonPreview",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL
)
@Composable
fun FilledRoundedButtonPreview() {
    DVPropertiesTheme {
        FilledRoundedButton(onClick = {}) {
            Text(text = "Test")
        }
    }
}

@Preview(
    name = "OutlinedRoundedButtonPreview",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL
)
@Composable
fun OutlinedRoundedButtonPreview() {
    DVPropertiesTheme {
        OutlinedRoundedButton(onClick = {}) {
            Text(text = "Test")
        }
    }
}
