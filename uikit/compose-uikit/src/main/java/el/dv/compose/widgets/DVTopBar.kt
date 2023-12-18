package el.dv.compose.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import el.dv.compose.theme.dvpropeerties.DVPropertiesTheme
import el.dv.compose.theme.paddingEndSmall
import el.dv.compose.theme.paddingHorzXLarge
import el.dv.compose.theme.paddingVert
import el.dv.compose_uikit.R

@Composable
fun DVTopBar(
    scaffoldState: ScaffoldState,
    scrollState: ScrollState,
    title: String,
    imageVector: ImageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
    arrowColor: Color = MaterialTheme.colors.background,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
    ) {
        Row(
            modifier = Modifier
                .paddingHorzXLarge()
                .paddingVert()
        ) {
            DVBackArrowIcon(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                imageVector = imageVector,
                arrowColor = arrowColor,
                onClick = {
                    onBack()
                }
            )

            DVSpacer(modifier = Modifier.paddingEndSmall())

            H3Text(text = title, color = MaterialTheme.colors.background)
        }
    }
}

@Composable
fun DVBackArrowIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    arrowColor: Color,
    onClick: () -> Unit
) {
    Image(
        imageVector = imageVector,
        contentDescription = null,
        modifier = modifier
            .clickable {
                onClick()
            },
        colorFilter = ColorFilter.tint(arrowColor)
    )
}

@Preview(
    showSystemUi = true,
    name = "DVTopBarPreview",
    device = Devices.PIXEL_4_XL
)
@Composable
fun DVTopBarPreview() {
    DVPropertiesTheme {
        DVTopBar(scaffoldState = rememberScaffoldState(), scrollState = rememberScrollState(), title = "Back") {}
    }
}
