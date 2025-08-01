package el.dv.dvproperties.feature.propertydetails.composables

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import el.dv.compose.theme.Dimens
import el.dv.compose.theme.dvpropeerties.DVPropertiesTheme
import el.dv.compose.theme.dvpropeerties.dvPropertiesCardColors
import el.dv.compose.theme.dvpropeerties.dvPropertiesCardElevation
import el.dv.compose.theme.paddingBottomMedium
import el.dv.compose.theme.paddingBottomXXSmall
import el.dv.compose.theme.paddingEndMedium
import el.dv.compose.theme.paddingEndXXSmall
import el.dv.compose.theme.paddingHorzMedium
import el.dv.compose.theme.paddingTopMedium
import el.dv.compose.theme.paddingXSmall
import el.dv.compose.widgets.BodyText
import el.dv.compose.widgets.DVSpacer
import el.dv.compose.widgets.H2Text
import el.dv.compose.widgets.H3Text
import el.dv.data.extension.formatToCurrency
import el.dv.domain.dvproperties.propertydetails.model.ImagePaths
import el.dv.domain.dvproperties.propertydetails.model.PropertyDetails
import el.dv.domain.dvproperties.propertydetails.model.PropertyType
import el.dv.dvproperties.R

@Composable
fun PropertyDetailsScreen(
    scrollState: ScrollState,
    propertyDetails: PropertyDetails
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .paddingTopMedium()
                .paddingHorzMedium()
                .verticalScroll(scrollState)
                .matchParentSize()
        ) {
            PropertyImageGrid(imagePaths = propertyDetails.imagePaths)

            DVSpacer(modifier = Modifier.paddingBottomMedium())

            PrimaryPropertyDetail(propertyDetails = propertyDetails)

            DVSpacer(modifier = Modifier.paddingBottomMedium())

            SecondaryPropertyDetails(propertyDetails = propertyDetails)
        }
    }
}

@Composable
fun PropertyImageGrid(
    imagePaths: ImagePaths
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center
    ) {
        when (imagePaths.imagePathsList.isEmpty()) {
            true -> item {
                NoPropertyImageItem()
            }
            false -> items(imagePaths.imagePathsList.map { it.toUri() }) { item ->
                PropertyImageItem(imagePath = item)
            }
        }
    }
}

@Composable
fun PropertyImageItem(imagePath: Uri) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            model = ImageRequest.Builder(LocalContext.current).data(imagePath).build(),
            contentDescription = null
        )
    }
}

@Composable
fun NoPropertyImageItem() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.no_image),
            contentDescription = null
        )
    }
}

@Composable
fun PrimaryPropertyDetail(propertyDetails: PropertyDetails) {
    Column {
        H2Text(text = propertyDetails.propertyCost)

        DVSpacer(modifier = Modifier.paddingBottomXXSmall())

        PropertyDimensions(propertyDetails = propertyDetails)

        BodyText(text = propertyDetails.fullAddress())
    }
}

@Composable
fun PropertyDimensions(propertyDetails: PropertyDetails) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            H3Text(text = propertyDetails.bedroomCount)

            DVSpacer(modifier = Modifier.paddingEndXXSmall())

            BodyText(text = stringResource(id = R.string.bedrooms_short))
        }

        DVSpacer(modifier = Modifier.paddingEndMedium())

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            H3Text(text = propertyDetails.bathroomCount)

            DVSpacer(modifier = Modifier.paddingEndXXSmall())

            BodyText(text = stringResource(id = R.string.bathrooms_short))
        }

        DVSpacer(modifier = Modifier.paddingEndMedium())

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            H3Text(text = propertyDetails.propertySize)

            DVSpacer(modifier = Modifier.paddingEndXXSmall())

            BodyText(text = stringResource(id = R.string.square_feet))
        }
    }
}

@Composable
fun SecondaryPropertyDetails(propertyDetails: PropertyDetails) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                shape = RoundedCornerShape(Dimens.CornerRadius.small),
                colors = dvPropertiesCardColors(),
                elevation = dvPropertiesCardElevation()
            ) {
                BodyText(modifier = Modifier.paddingXSmall(), text = propertyDetails.propertyType.getStringResource())
            }

            DVSpacer(modifier = Modifier.paddingEndMedium())

            Card(
                shape = RoundedCornerShape(Dimens.CornerRadius.small),
                colors = dvPropertiesCardColors(),
                elevation = dvPropertiesCardElevation()
            ) {
                BodyText(modifier = Modifier.paddingXSmall(), text = stringResource(id = R.string.built_in, propertyDetails.buildDate))
            }
        }

        DVSpacer()

        Row {
            Card(
                shape = RoundedCornerShape(Dimens.CornerRadius.small),
                colors = dvPropertiesCardColors(),
                elevation = dvPropertiesCardElevation()
            ) {
                BodyText(modifier = Modifier.paddingXSmall(), text = stringResource(id = R.string.sqft_lot, propertyDetails.lotSize))
            }
        }
    }
}

@Preview(
    name = "PropertyDetailsScreenPreview",
    showBackground = true,
    device = Devices.PIXEL_4_XL
)
@Composable
fun PropertyDetailsScreenPreview() {
    DVPropertiesTheme {
        PropertyDetailsScreen(scrollState = rememberScrollState(), propertyDetails = propertyDetailsMock)
    }
}

private val propertyDetailsMock = PropertyDetails(
    id = 1,
    address = "1330 Semillion Way",
    city = "Gonzales",
    state = "CA",
    zipCode = "93933",
    propertyCost = 500000.formatToCurrency(),
    bedroomCount = "3",
    bathroomCount = "2",
    propertySize = "1450",
    buildDate = "1959",
    lotSize = "8500"
)

@Composable
fun PropertyType.getStringResource(): String {
    return when (this) {
        PropertyType.SFH -> stringResource(id = R.string.single_family)
        PropertyType.Multi -> stringResource(id = R.string.multi_family)
        PropertyType.Commercial -> stringResource(id = R.string.commercial_property)
    }
}
