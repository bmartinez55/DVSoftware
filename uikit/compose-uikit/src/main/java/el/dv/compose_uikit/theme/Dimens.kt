package el.dv.compose_uikit.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Dimens {
    object Padding {
        val xxsmall = 4.dp
        val xsmall = 8.dp
        val small = 12.dp
        val medium = 16.dp
        val large = 20.dp
        val xlarge = 24.dp
        val x2large = 32.dp
        val x3large = 48.dp
        val x4large = 64.dp
    }

    object FontSize {
        val small = 14.sp
        val medium = 16.sp
        val large = 18.sp
        val xlarge = 24.sp
        val x2large = 32.sp
    }

    object CornerRadius {
        val small = 4.dp
        val medium = 8.dp
        val large = 10.dp
        val xlarge = 14.dp
    }

    object Border {
        val small = 1.dp
        val medium = 2.dp
        val large = 4.dp
    }

    object Elevation {
        val none = 0.dp
        val small = 4.dp
        val medium = 6.dp
        val large = 8.dp
    }
}

// Padding for all side of the view
fun Modifier.padding() = paddingMedium()
fun Modifier.paddingXXSmall() = padding(all = convertUnitToPadding(DimensUnit.XXSMALL))
fun Modifier.paddingXSmall() = padding(all = convertUnitToPadding(DimensUnit.XSMALL))
fun Modifier.paddingSmall() = padding(all = convertUnitToPadding(DimensUnit.SMALL))
fun Modifier.paddingMedium() = padding(all = convertUnitToPadding(DimensUnit.MEDIUM))
fun Modifier.paddingLarge() = padding(all = convertUnitToPadding(DimensUnit.LARGE))
fun Modifier.paddingXLarge() = padding(all = convertUnitToPadding(DimensUnit.XLARGE))
fun Modifier.paddingX2Large() = padding(all = convertUnitToPadding(DimensUnit.X2LARGE))
fun Modifier.paddingX3Large() = padding(all = convertUnitToPadding(DimensUnit.X3LARGE))
fun Modifier.paddingX4Large() = padding(all = convertUnitToPadding(DimensUnit.X4LARGE))
fun Modifier.noPadding() = padding(all = 0.dp)

// Vertical padding for views
fun Modifier.paddingVert() = paddingVertMedium()
fun Modifier.paddingVertXXSmall() = padding(vertical = convertUnitToPadding(DimensUnit.XXSMALL))
fun Modifier.paddingVertXSmall() = padding(vertical = convertUnitToPadding(DimensUnit.XSMALL))
fun Modifier.paddingVertSmall() = padding(vertical = convertUnitToPadding(DimensUnit.SMALL))
fun Modifier.paddingVertMedium() = padding(vertical = convertUnitToPadding(DimensUnit.MEDIUM))
fun Modifier.paddingVertLarge() = padding(vertical = convertUnitToPadding(DimensUnit.LARGE))
fun Modifier.paddingVertXLarge() = padding(vertical = convertUnitToPadding(DimensUnit.XLARGE))
fun Modifier.paddingVertX2Large() = padding(vertical = convertUnitToPadding(DimensUnit.X2LARGE))
fun Modifier.paddingVertX3Large() = padding(vertical = convertUnitToPadding(DimensUnit.X3LARGE))
fun Modifier.paddingVertX4Large() = padding(vertical = convertUnitToPadding(DimensUnit.X4LARGE))
fun Modifier.noVertPadding() = padding(vertical = 0.dp)

// Horizontal padding for views
fun Modifier.paddingHorz() = paddingHorzMedium()
fun Modifier.paddingHorzXXSmall() = padding(horizontal = convertUnitToPadding(DimensUnit.XXSMALL))
fun Modifier.paddingHorzXSmall() = padding(horizontal = convertUnitToPadding(DimensUnit.XSMALL))
fun Modifier.paddingHorzSmall() = padding(horizontal = convertUnitToPadding(DimensUnit.SMALL))
fun Modifier.paddingHorzMedium() = padding(horizontal = convertUnitToPadding(DimensUnit.MEDIUM))
fun Modifier.paddingHorzLarge() = padding(horizontal = convertUnitToPadding(DimensUnit.LARGE))
fun Modifier.paddingHorzXLarge() = padding(horizontal = convertUnitToPadding(DimensUnit.XLARGE))
fun Modifier.paddingHorzX2Large() = padding(horizontal = convertUnitToPadding(DimensUnit.X2LARGE))
fun Modifier.paddingHorzX3Large() = padding(horizontal = convertUnitToPadding(DimensUnit.X3LARGE))
fun Modifier.paddingHorzX4Large() = padding(horizontal = convertUnitToPadding(DimensUnit.X4LARGE))
fun Modifier.noHorzPadding() = padding(horizontal = 0.dp)

// Bottom padding for views
fun Modifier.paddingBottom() = paddingBottomMedium()
fun Modifier.paddingBottomXXSmall() = padding(bottom = convertUnitToPadding(DimensUnit.XXSMALL))
fun Modifier.paddingBottomXSmall() = padding(bottom = convertUnitToPadding(DimensUnit.XSMALL))
fun Modifier.paddingBottomSmall() = padding(bottom = convertUnitToPadding(DimensUnit.SMALL))
fun Modifier.paddingBottomMedium() = padding(bottom = convertUnitToPadding(DimensUnit.MEDIUM))
fun Modifier.paddingBottomLarge() = padding(bottom = convertUnitToPadding(DimensUnit.LARGE))
fun Modifier.paddingBottomXLarge() = padding(bottom = convertUnitToPadding(DimensUnit.XLARGE))
fun Modifier.paddingBottomX2Large() = padding(bottom = convertUnitToPadding(DimensUnit.X2LARGE))
fun Modifier.paddingBottomX3Large() = padding(bottom = convertUnitToPadding(DimensUnit.X3LARGE))
fun Modifier.paddingBottomX4Large() = padding(bottom = convertUnitToPadding(DimensUnit.X4LARGE))
fun Modifier.noBottomPadding() = padding(bottom = 0.dp)

// Top padding for views
fun Modifier.paddingTop() = paddingTopMedium()
fun Modifier.paddingTopXXSmall() = padding(top = convertUnitToPadding(DimensUnit.XXSMALL))
fun Modifier.paddingTopXSmall() = padding(top = convertUnitToPadding(DimensUnit.XSMALL))
fun Modifier.paddingTopSmall() = padding(top = convertUnitToPadding(DimensUnit.SMALL))
fun Modifier.paddingTopMedium() = padding(top = convertUnitToPadding(DimensUnit.MEDIUM))
fun Modifier.paddingTopLarge() = padding(top = convertUnitToPadding(DimensUnit.LARGE))
fun Modifier.paddingTopXLarge() = padding(top = convertUnitToPadding(DimensUnit.XLARGE))
fun Modifier.paddingTopX2Large() = padding(top = convertUnitToPadding(DimensUnit.X2LARGE))
fun Modifier.paddingTopX3Large() = padding(top = convertUnitToPadding(DimensUnit.X3LARGE))
fun Modifier.paddingTopX4Large() = padding(top = convertUnitToPadding(DimensUnit.X4LARGE))
fun Modifier.noTopPadding() = padding(top = 0.dp)

// Start padding for views
fun Modifier.paddingStart() = paddingStartMedium()
fun Modifier.paddingStartXXSmall() = padding(start = convertUnitToPadding(DimensUnit.XXSMALL))
fun Modifier.paddingStartXSmall() = padding(start = convertUnitToPadding(DimensUnit.XSMALL))
fun Modifier.paddingStartSmall() = padding(start = convertUnitToPadding(DimensUnit.SMALL))
fun Modifier.paddingStartMedium() = padding(start = convertUnitToPadding(DimensUnit.MEDIUM))
fun Modifier.paddingStartLarge() = padding(start = convertUnitToPadding(DimensUnit.LARGE))
fun Modifier.paddingStartXLarge() = padding(start = convertUnitToPadding(DimensUnit.XLARGE))
fun Modifier.paddingStartX2Large() = padding(start = convertUnitToPadding(DimensUnit.X2LARGE))
fun Modifier.paddingStartX3Large() = padding(start = convertUnitToPadding(DimensUnit.X3LARGE))
fun Modifier.paddingStartX4Large() = padding(start = convertUnitToPadding(DimensUnit.X4LARGE))
fun Modifier.noStartPadding() = padding(start = 0.dp)

// End padding for views
fun Modifier.paddingEnd() = paddingEndMedium()
fun Modifier.paddingEndXXSmall() = padding(end = convertUnitToPadding(DimensUnit.XXSMALL))
fun Modifier.paddingEndXSmall() = padding(end = convertUnitToPadding(DimensUnit.XSMALL))
fun Modifier.paddingEndSmall() = padding(end = convertUnitToPadding(DimensUnit.SMALL))
fun Modifier.paddingEndMedium() = padding(end = convertUnitToPadding(DimensUnit.MEDIUM))
fun Modifier.paddingEndLarge() = padding(end = convertUnitToPadding(DimensUnit.LARGE))
fun Modifier.paddingEndXLarge() = padding(end = convertUnitToPadding(DimensUnit.XLARGE))
fun Modifier.paddingEndX2Large() = padding(end = convertUnitToPadding(DimensUnit.X2LARGE))
fun Modifier.paddingEndX3Large() = padding(end = convertUnitToPadding(DimensUnit.X3LARGE))
fun Modifier.paddingEndX4Large() = padding(end = convertUnitToPadding(DimensUnit.X4LARGE))
fun Modifier.noEndPadding() = padding(end = 0.dp)

private fun convertUnitToPadding(unit: DimensUnit) = when (unit) {
    DimensUnit.XXSMALL -> Dimens.Padding.xxsmall
    DimensUnit.XSMALL -> Dimens.Padding.xsmall
    DimensUnit.SMALL -> Dimens.Padding.small
    DimensUnit.MEDIUM -> Dimens.Padding.medium
    DimensUnit.LARGE -> Dimens.Padding.large
    DimensUnit.XLARGE -> Dimens.Padding.xlarge
    DimensUnit.X2LARGE -> Dimens.Padding.x2large
    DimensUnit.X3LARGE -> Dimens.Padding.x3large
    DimensUnit.X4LARGE -> Dimens.Padding.x4large
}

enum class DimensUnit {
    XXSMALL,
    XSMALL,
    SMALL,
    MEDIUM,
    LARGE,
    XLARGE,
    X2LARGE,
    X3LARGE,
    X4LARGE
}
