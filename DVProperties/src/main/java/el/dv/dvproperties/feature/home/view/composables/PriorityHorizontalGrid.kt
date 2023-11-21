package el.dv.dvproperties.feature.home.view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import el.dv.compose.theme.Dimens
import el.dv.compose.theme.dvpropeerties.DVPropertiesTheme
import el.dv.compose.theme.paddingBottomSmall
import el.dv.compose.theme.paddingMedium
import el.dv.compose.widgets.BodyText
import el.dv.compose.widgets.DVSpacer
import el.dv.compose.widgets.H3Text
import el.dv.domain.dvproperties.propertydetails.model.PropertyDetails
import el.dv.dvproperties.R

@Composable
fun PriorityHorizontalGrid(
    propertyList: List<PropertyDetails>,
    propertyDetailsItemOnClick: (Int) -> Unit,
    addPropertyDetailsItemOnClick: () -> Unit
) {
    when (propertyList.isEmpty()) {
        true -> AddPropertyItem(addPropertyDetailsItemOnClick = { addPropertyDetailsItemOnClick() })
        false -> LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(propertyList) { item ->
                PropertyDetailsItem(propertyDetails = item) {
                    propertyDetailsItemOnClick(it)
                }
            }
        }
    }
}

@Composable
fun AddPropertyItem(
    addPropertyDetailsItemOnClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable {
                addPropertyDetailsItemOnClick()
            },
        shape = RoundedCornerShape(Dimens.CornerRadius.large),
        elevation = Dimens.Elevation.medium,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            Modifier.paddingMedium()

        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.ic_add_circle),
                contentDescription = null
            )

            DVSpacer(modifier = Modifier.paddingBottomSmall())

            H3Text(text = stringResource(id = R.string.add_property))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PropertyDetailsItem(
    propertyDetails: PropertyDetails,
    propertyDetailsItemOnClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(Dimens.CornerRadius.large),
        elevation = Dimens.Elevation.small,
        onClick = {
            propertyDetailsItemOnClick(propertyDetails.id)
        }
    ) {
        Column(
            modifier = Modifier.paddingMedium()
        ) {
            Image(painter = painterResource(id = R.drawable.ic_default_image), contentDescription = null)

            H3Text(text = propertyDetails.address)

            BodyText(text = stringResource(id = R.string.city_plus_state, propertyDetails.city, propertyDetails.state))
        }
    }
}

@Preview(
    name = "PropertyDetailsItemPreview",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL
)
@Composable
fun PropertyDetailsItemPreview() {
    DVPropertiesTheme {
        PropertyDetailsItem(propertyDetails = PropertyDetailsMockData.propertyDetails) {}
    }
}

private object PropertyDetailsMockData {
    val propertyDetails: PropertyDetails = PropertyDetails(
        id = 123,
        address = "123 Main St",
        city = "Salinas",
        state = "CA",
        zipCode = "93905",
        propertyCost = "500000"
    )
}
