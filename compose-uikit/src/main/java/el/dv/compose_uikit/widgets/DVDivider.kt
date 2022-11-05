package el.dv.compose_uikit.widgets

import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DVDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onBackground
) {
    Divider(modifier = modifier, color = color)
}
