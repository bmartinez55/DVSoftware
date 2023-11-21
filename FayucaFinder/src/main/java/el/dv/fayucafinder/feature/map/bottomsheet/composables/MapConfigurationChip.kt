package el.dv.fayucafinder.feature.map.bottomsheet.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import el.dv.compose.theme.fayucafinder.FayucaFinderColor
import el.dv.compose.theme.paddingSmall
import el.dv.compose.theme.paddingXSmall
import el.dv.compose.widgets.BodyText
import el.dv.domain.navigation.model.MapVisualType
import el.dv.fayucafinder.feature.map.bottomsheet.MapTypeChip

@Composable
fun MapConfigurationChip(
    chip: MapTypeChip,
    clickedChip: MapVisualType,
    onMapVisualTypeChipClick: (MapVisualType) -> Unit
) {
    val isSelected = clickedChip == chip.mapVisualType
    val selectedColor = if (isSelected) FayucaFinderColor.Palette.deepOrange500 else FayucaFinderColor.Palette.grey500

    Column {
        Box(
            modifier = Modifier
                .paddingXSmall()
                .wrapContentHeight()
                .clip(CircleShape)
                .background(color = FayucaFinderColor.Palette.white, shape = CircleShape)
                .border(BorderStroke(width = 2.dp, color = selectedColor), shape = CircleShape)
                .clickable {
                    onMapVisualTypeChipClick(chip.mapVisualType)
                }
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessVeryLow
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .paddingSmall()
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(visible = isSelected) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = selectedColor
                    )
                }
                BodyText(text = chip.title, color = selectedColor)
            }
        }
    }
}
