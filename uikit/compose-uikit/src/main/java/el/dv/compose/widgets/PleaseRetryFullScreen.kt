package el.dv.compose.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import el.dv.compose.theme.dvpropeerties.DVPropertiesTheme
import el.dv.compose_uikit.R

@Composable
fun PleaseRetryFullScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp),
                painter = painterResource(id = R.drawable.ic_report),
                contentDescription = null
            )
            H2Text(text = stringResource(id = R.string.something_went_wrong))
            BodyText(text = stringResource(id = R.string.please_try_again))
        }
    }
}

@Preview(
    name = "PleaseRetryFullScreen",
    showBackground = true,
    device = Devices.PIXEL_XL
)
@Composable
fun PleaseRetryFullScreenPreview() {
    DVPropertiesTheme {
        PleaseRetryFullScreen()
    }
}
