package el.dv.dvproperties.feature.home.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import el.dv.compose.theme.dvpropeerties.DVPropertiesTheme
import el.dv.compose.theme.paddingHorzMedium
import el.dv.compose.theme.paddingTopX3Large
import el.dv.compose.widgets.H2Text
import el.dv.presentation.R

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paddingHorzMedium()
    ) {
        H2Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.loading)
        )
        LinearProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .paddingTopX3Large()
        )
    }
}

@Composable
@Preview
fun LoadingScreenPreview() {
    DVPropertiesTheme {
        LoadingScreen()
    }
}
