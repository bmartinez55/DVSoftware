package el.dv.compose_uikit.widgets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import el.dv.compose_uikit.theme.paddingBottom

@Composable
fun DVSpacer(
    modifier: Modifier = Modifier.paddingBottom()
) {
    Spacer(modifier = modifier)
}
