package el.dv.fayucafinder.feature.login.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import el.dv.compose_uikit.extension.angledGradientBackground
import el.dv.compose_uikit.theme.FayucaFinderColor
import el.dv.fayucafinder.R
import el.dv.fayucafinder.core.view.FayucaFinderScreen

@Composable
fun LoginScreen(
    loginState: LoginState,

) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .angledGradientBackground(
                colors = listOf(
                    FayucaFinderColor.Palette.deepOrange500,
                    FayucaFinderColor.Palette.deepOrange500Light
                ),
                angle = 135f
            )
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(200.dp),
                painter = painterResource(id = R.drawable.fayuca_finder_logo_name),
                contentDescription = null
            )


        }
    }
}

@Preview(
    showSystemUi = true,
    device = Devices.PIXEL_4
)
@Composable
fun FayucaFinderScreenPreview() {
    LoginScreen(loginState = LoginState.Show)
}
