package el.dv.weatherapp.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import el.dv.compose.extension.angledGradientBackground
import el.dv.compose.theme.paddingBottomX4Large
import el.dv.compose.theme.paddingHorzX4Large
import el.dv.compose.theme.paddingXLarge
import el.dv.compose.widgets.DVSpacer
import el.dv.compose.widgets.FilledRoundedButton
import el.dv.weatherapp.R
import el.dv.weatherapp.navhost.NavItem
import el.dv.weatherapp.ui.theme.WeatherAppTheme
import el.dv.weatherapp.ui.theme.yellow

@Composable
fun HomeScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .angledGradientBackground(
                listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary),
                45f
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paddingXLarge()
        ) {
            DVSpacer(modifier = Modifier.paddingBottomX4Large())

            Image(
                painter = painterResource(R.drawable.sun_cloud),
                contentDescription = null
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .paddingBottomX4Large()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.weather),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.surface,
                fontSize = 72.sp
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.forecasts),
                style = MaterialTheme.typography.bodyLarge,
                color = yellow,
                fontSize = 72.sp
            )

            DVSpacer(modifier = Modifier.paddingBottomX4Large())

            FilledRoundedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .paddingHorzX4Large()
                    .align(Alignment.CenterHorizontally),
                shape = MaterialTheme.shapes.extraLarge,
                onClick = {
                    navController.navigate(NavItem.Search.route)
                },
                content = {
                    Text(
                        stringResource(R.string.get_started)
                    )
                }
            )

            DVSpacer(modifier = Modifier.paddingBottomX4Large())
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_7_PRO
)
@Composable
fun HomeScreenPreview() {
    WeatherAppTheme {
        HomeScreen(rememberNavController())
    }
}
