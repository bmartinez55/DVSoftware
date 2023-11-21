package el.dv.dvproperties.feature.home.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import el.dv.compose.theme.dvpropeerties.DVPropertiesTheme
import el.dv.compose.theme.paddingBottomX4Large
import el.dv.compose.theme.paddingHorzX4Large
import el.dv.compose.theme.paddingTopX4Large
import el.dv.compose.widgets.FilledRoundedButton
import el.dv.compose.widgets.H3Text
import el.dv.presentation.R

@Composable
fun ErrorScreen(retryButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.primaryVariant)
            .fillMaxSize()
    ) {
        H3Text(
            text = stringResource(id = R.string.error_please_try_again),
            modifier = Modifier
                .align(Alignment.Center)
                .paddingBottomX4Large()
        )

        FilledRoundedButton(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .paddingHorzX4Large()
                .paddingTopX4Large(),
            onClick = { retryButtonClick() }
        ) {
            H3Text(
                text = stringResource(id = R.string.retry),
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
}

@Composable
@Preview(
    name = "ErrorScreenPreview",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL
)
fun ErrorScreenPreview() {
    DVPropertiesTheme {
        ErrorScreen {}
    }
}
