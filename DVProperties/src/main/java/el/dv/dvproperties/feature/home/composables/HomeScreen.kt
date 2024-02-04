package el.dv.dvproperties.feature.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import el.dv.compose.theme.dvpropeerties.DVPropertiesTheme
import el.dv.compose.theme.paddingBottomMedium
import el.dv.compose.theme.paddingBottomXSmall
import el.dv.compose.theme.paddingMedium
import el.dv.compose.widgets.DVSpacer
import el.dv.compose.widgets.H1Text
import el.dv.compose.widgets.H3Text
import el.dv.domain.dvproperties.propertydetails.model.PropertyDetails
import el.dv.presentation.R

@Composable
fun HomeScreen(
    propertyList: List<PropertyDetails>,
    propertyDetailsItemOnClick: (Int) -> Unit,
    addPropertyDetailsItemOnClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingMedium()
            .background(MaterialTheme.colors.background)
    ) {
        H1Text(text = stringResource(id = R.string.welcome))

        DVSpacer(modifier = Modifier.paddingBottomMedium())

        H3Text(text = stringResource(id = R.string.recent))

        DVSpacer(modifier = Modifier.paddingBottomXSmall())

        PriorityHorizontalGrid(
            propertyList = propertyList,
            propertyDetailsItemOnClick = { propertyDetailsItemOnClick(it) },
            addPropertyDetailsItemOnClick = { addPropertyDetailsItemOnClick() }
        )
    }
}

@Preview(
    name = "HomeScreenPreview",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL
)
@Composable
fun HomeScreenPreview() {
    DVPropertiesTheme {
        HomeScreen(propertyList = PropertyDetailsListMockData.noPropertyDetails, {}, {})
    }
}

private object PropertyDetailsListMockData {
    val propertyDetailsList = listOf(
        PropertyDetails(
            id = 123,
            address = "123 Main St",
            city = "Salinas",
            state = "CA",
            propertyCost = "500000"
        )
    )

    val noPropertyDetails = emptyList<PropertyDetails>()
}
