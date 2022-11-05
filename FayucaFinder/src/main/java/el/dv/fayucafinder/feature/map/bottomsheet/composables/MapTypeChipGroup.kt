package el.dv.fayucafinder.feature.map.bottomsheet.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import el.dv.domain.navigation.model.MapVisualType
import el.dv.fayucafinder.feature.map.bottomsheet.MapTypeChip

@Composable
fun MapTypeChipGroup(
    mapVisualTypeChips: List<MapTypeChip>,
    currentMapVisualType: MapVisualType,
    onMapVisualTypeChipClick: (MapVisualType) -> Unit
) {
    var clickedChip by remember { mutableStateOf(value = currentMapVisualType) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(items = mapVisualTypeChips) { chip ->
            MapConfigurationChip(
                chip = chip,
                clickedChip = clickedChip,
                onMapVisualTypeChipClick = { mapVisualType ->
                    onMapVisualTypeChipClick(mapVisualType)
                    clickedChip = mapVisualType
                }
            )
        }
    }
}

@Preview(
    showSystemUi = true,
    device = Devices.PIXEL_4_XL
)
@Composable
fun PreviewMapConfigurationChipGroup() {
    MapTypeChipGroup(
        mapVisualTypeChips = listOf(
            MapTypeChip(mapVisualType = MapVisualType.Normal, title = "Normal"),
            MapTypeChip(mapVisualType = MapVisualType.Terrain, title = "Terrain"),
            MapTypeChip(mapVisualType = MapVisualType.Terrain, title = "Hybrid")
        ),
        currentMapVisualType = MapVisualType.Normal,
        onMapVisualTypeChipClick = {}
    )
}
