package el.dv.fayucafinder.feature.login.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import el.dv.compose_uikit.extension.angledGradientBackground
import el.dv.compose_uikit.theme.Dimens
import el.dv.compose_uikit.theme.fayucafinder.FayucaFinderColor
import el.dv.compose_uikit.theme.fayucafinder.FayucaFinderTheme
import el.dv.compose_uikit.theme.paddingBottomX2Large
import el.dv.compose_uikit.theme.paddingHorzXLarge
import el.dv.compose_uikit.theme.paddingMedium
import el.dv.compose_uikit.theme.paddingTopX4Large
import el.dv.compose_uikit.widgets.BodyText
import el.dv.compose_uikit.widgets.DVTextField
import el.dv.compose_uikit.widgets.H2Text
import el.dv.compose_uikit.widgets.RoundedIconWhiteFilledButton
import el.dv.fayucafinder.R
import el.dv.presentation.R as PresentationR
import el.dv.presentation.extension.ActionListener

@Composable
fun LoginScreen(
    welcomeText: String,
    signInWithGoogleText: String,
    signInWithGoogleButtonIcon: Int,
    onSignInWithGoogleClick: ActionListener
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
                .fillMaxSize()
                .paddingTopX4Large()
        ) {
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.fayuca_finder_logo_name),
                contentDescription = null
            )
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .paddingTopX4Large(),
                elevation = Dimens.Elevation.small,
                shape = RoundedCornerShape(
                    topStart = Dimens.CornerRadius.medium,
                    topEnd = Dimens.CornerRadius.medium
                ),
                backgroundColor = MaterialTheme.colors.background
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .paddingMedium()
                ) {
                    Column {
                        H2Text(text = welcomeText)
                    }
                    RoundedIconWhiteFilledButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .paddingHorzXLarge()
                            .paddingBottomX2Large(),
                        text = signInWithGoogleText,
                        icon = signInWithGoogleButtonIcon,
                        onClick = onSignInWithGoogleClick
                    )
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    device = Devices.PIXEL_4
)
@Composable
fun FayucaFinderScreenPreview() {
    FayucaFinderTheme {
        LoginScreen(
            welcomeText = "Welcome!",
            signInWithGoogleText = "Sign In With Google",
            signInWithGoogleButtonIcon = PresentationR.drawable.ic_google_icon
        ) {}
    }
}
