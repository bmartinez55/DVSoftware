package el.dv.fayucafinder.feature.map.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import el.dv.compose_uikit.theme.FayucaFinderTheme
import el.dv.compose_uikit.theme.padding
import el.dv.compose_uikit.widgets.DVSpacer
import el.dv.compose_uikit.widgets.H2Text
import el.dv.domain.navigation.model.MapVisualType
import el.dv.fayucafinder.feature.map.bottomsheet.composables.MapTypeChipGroup

@Composable
fun MapConfigurationScreen(
    mapTypeTitle: String,
    currentMapVisualType: MapVisualType,
    mapTypeChipList: List<MapTypeChip>,
    onMapVisualTypeChipClick: (MapVisualType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding()
    ) {
        Row(modifier = Modifier) {
            H2Text(text = mapTypeTitle, modifier = Modifier)
        }
        DVSpacer()
        MapTypeChipGroup(
            mapVisualTypeChips = mapTypeChipList,
            currentMapVisualType = currentMapVisualType,
            onMapVisualTypeChipClick = { mapVisualType ->
                onMapVisualTypeChipClick(mapVisualType)
            }
        )
        DVSpacer()
    }
}

@Preview(
    showSystemUi = true,
    device = Devices.PIXEL_4_XL
)
@Composable
fun PreviewMapConfigurationScreen() {
    FayucaFinderTheme {
        MapConfigurationScreen(
            mapTypeTitle = "Map Configurations",
            currentMapVisualType = MapVisualType.Normal,
            mapTypeChipList = listOf(MapTypeChip(mapVisualType = MapVisualType.Normal, title = "Normal")),
            onMapVisualTypeChipClick = {}
        )
    }
}
