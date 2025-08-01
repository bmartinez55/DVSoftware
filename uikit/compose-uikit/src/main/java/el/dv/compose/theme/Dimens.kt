package el.dv.compose.theme

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
fun Modifier.paddingXXSmall() = this.padding(all = convertUnitToPadding(DimensUnit.XXSMALL))
fun Modifier.paddingXSmall() = this.padding(all = convertUnitToPadding(DimensUnit.XSMALL))
fun Modifier.paddingSmall() = this.padding(all = convertUnitToPadding(DimensUnit.SMALL))
fun Modifier.paddingMedium() = this.padding(all = convertUnitToPadding(DimensUnit.MEDIUM))
fun Modifier.paddingLarge() = this.padding(all = convertUnitToPadding(DimensUnit.LARGE))
fun Modifier.paddingXLarge() = this.padding(all = convertUnitToPadding(DimensUnit.XLARGE))
fun Modifier.paddingX2Large() = this.padding(all = convertUnitToPadding(DimensUnit.X2LARGE))
fun Modifier.paddingX3Large() = this.padding(all = convertUnitToPadding(DimensUnit.X3LARGE))
fun Modifier.paddingX4Large() = this.padding(all = convertUnitToPadding(DimensUnit.X4LARGE))
fun Modifier.noPadding() = this.padding(all = 0.dp)

// Vertical padding for views
fun Modifier.paddingVert() = paddingVertMedium()
fun Modifier.paddingVertXXSmall() = this.padding(vertical = convertUnitToPadding(DimensUnit.XXSMALL))
fun Modifier.paddingVertXSmall() = this.padding(vertical = convertUnitToPadding(DimensUnit.XSMALL))
fun Modifier.paddingVertSmall() = this.padding(vertical = convertUnitToPadding(DimensUnit.SMALL))
fun Modifier.paddingVertMedium() = this.padding(vertical = convertUnitToPadding(DimensUnit.MEDIUM))
fun Modifier.paddingVertLarge() = this.padding(vertical = convertUnitToPadding(DimensUnit.LARGE))
fun Modifier.paddingVertXLarge() = this.padding(vertical = convertUnitToPadding(DimensUnit.XLARGE))
fun Modifier.paddingVertX2Large() = this.padding(vertical = convertUnitToPadding(DimensUnit.X2LARGE))
fun Modifier.paddingVertX3Large() = this.padding(vertical = convertUnitToPadding(DimensUnit.X3LARGE))
fun Modifier.paddingVertX4Large() = this.padding(vertical = convertUnitToPadding(DimensUnit.X4LARGE))
fun Modifier.noVertPadding() = this.padding(vertical = 0.dp)

// Horizontal padding for views
fun Modifier.paddingHorz() = paddingHorzMedium()
fun Modifier.paddingHorzXXSmall() = this.padding(horizontal = convertUnitToPadding(DimensUnit.XXSMALL))
fun Modifier.paddingHorzXSmall() = this.padding(horizontal = convertUnitToPadding(DimensUnit.XSMALL))
fun Modifier.paddingHorzSmall() = this.padding(horizontal = convertUnitToPadding(DimensUnit.SMALL))
fun Modifier.paddingHorzMedium() = this.padding(horizontal = convertUnitToPadding(DimensUnit.MEDIUM))
fun Modifier.paddingHorzLarge() = this.padding(horizontal = convertUnitToPadding(DimensUnit.LARGE))
fun Modifier.paddingHorzXLarge() = this.padding(horizontal = convertUnitToPadding(DimensUnit.XLARGE))
fun Modifier.paddingHorzX2Large() = this.padding(horizontal = convertUnitToPadding(DimensUnit.X2LARGE))
fun Modifier.paddingHorzX3Large() = this.padding(horizontal = convertUnitToPadding(DimensUnit.X3LARGE))
fun Modifier.paddingHorzX4Large() = this.padding(horizontal = convertUnitToPadding(DimensUnit.X4LARGE))
fun Modifier.noHorzPadding() = this.padding(horizontal = 0.dp)

// Bottom padding for views
fun Modifier.paddingBottom() = paddingBottomMedium()
fun Modifier.paddingBottomXXSmall() = this.padding(bottom = convertUnitToPadding(DimensUnit.XXSMALL))
fun Modifier.paddingBottomXSmall() = this.padding(bottom = convertUnitToPadding(DimensUnit.XSMALL))
fun Modifier.paddingBottomSmall() = this.padding(bottom = convertUnitToPadding(DimensUnit.SMALL))
fun Modifier.paddingBottomMedium() = this.padding(bottom = convertUnitToPadding(DimensUnit.MEDIUM))
fun Modifier.paddingBottomLarge() = this.padding(bottom = convertUnitToPadding(DimensUnit.LARGE))
fun Modifier.paddingBottomXLarge() = this.padding(bottom = convertUnitToPadding(DimensUnit.XLARGE))
fun Modifier.paddingBottomX2Large() = this.padding(bottom = convertUnitToPadding(DimensUnit.X2LARGE))
fun Modifier.paddingBottomX3Large() = this.padding(bottom = convertUnitToPadding(DimensUnit.X3LARGE))
fun Modifier.paddingBottomX4Large() = this.padding(bottom = convertUnitToPadding(DimensUnit.X4LARGE))
fun Modifier.noBottomPadding() = this.padding(bottom = 0.dp)

// Top padding for views
fun Modifier.paddingTop() = paddingTopMedium()
fun Modifier.paddingTopXXSmall() = this.padding(top = convertUnitToPadding(DimensUnit.XXSMALL))
fun Modifier.paddingTopXSmall() = this.padding(top = convertUnitToPadding(DimensUnit.XSMALL))
fun Modifier.paddingTopSmall() = this.padding(top = convertUnitToPadding(DimensUnit.SMALL))
fun Modifier.paddingTopMedium() = this.padding(top = convertUnitToPadding(DimensUnit.MEDIUM))
fun Modifier.paddingTopLarge() = this.padding(top = convertUnitToPadding(DimensUnit.LARGE))
fun Modifier.paddingTopXLarge() = this.padding(top = convertUnitToPadding(DimensUnit.XLARGE))
fun Modifier.paddingTopX2Large() = this.padding(top = convertUnitToPadding(DimensUnit.X2LARGE))
fun Modifier.paddingTopX3Large() = this.padding(top = convertUnitToPadding(DimensUnit.X3LARGE))
fun Modifier.paddingTopX4Large() = this.padding(top = convertUnitToPadding(DimensUnit.X4LARGE))
fun Modifier.noTopPadding() = this.padding(top = 0.dp)

// Start padding for views
fun Modifier.paddingStart() = paddingStartMedium()
fun Modifier.paddingStartXXSmall() = this.padding(start = convertUnitToPadding(DimensUnit.XXSMALL))
fun Modifier.paddingStartXSmall() = this.padding(start = convertUnitToPadding(DimensUnit.XSMALL))
fun Modifier.paddingStartSmall() = this.padding(start = convertUnitToPadding(DimensUnit.SMALL))
fun Modifier.paddingStartMedium() = this.padding(start = convertUnitToPadding(DimensUnit.MEDIUM))
fun Modifier.paddingStartLarge() = this.padding(start = convertUnitToPadding(DimensUnit.LARGE))
fun Modifier.paddingStartXLarge() = this.padding(start = convertUnitToPadding(DimensUnit.XLARGE))
fun Modifier.paddingStartX2Large() = this.padding(start = convertUnitToPadding(DimensUnit.X2LARGE))
fun Modifier.paddingStartX3Large() = this.padding(start = convertUnitToPadding(DimensUnit.X3LARGE))
fun Modifier.paddingStartX4Large() = this.padding(start = convertUnitToPadding(DimensUnit.X4LARGE))
fun Modifier.noStartPadding() = this.padding(start = 0.dp)

// End padding for views
fun Modifier.paddingEnd() = paddingEndMedium()
fun Modifier.paddingEndXXSmall() = this.padding(end = convertUnitToPadding(DimensUnit.XXSMALL))
fun Modifier.paddingEndXSmall() = this.padding(end = convertUnitToPadding(DimensUnit.XSMALL))
fun Modifier.paddingEndSmall() = this.padding(end = convertUnitToPadding(DimensUnit.SMALL))
fun Modifier.paddingEndMedium() = this.padding(end = convertUnitToPadding(DimensUnit.MEDIUM))
fun Modifier.paddingEndLarge() = this.padding(end = convertUnitToPadding(DimensUnit.LARGE))
fun Modifier.paddingEndXLarge() = this.padding(end = convertUnitToPadding(DimensUnit.XLARGE))
fun Modifier.paddingEndX2Large() = this.padding(end = convertUnitToPadding(DimensUnit.X2LARGE))
fun Modifier.paddingEndX3Large() = this.padding(end = convertUnitToPadding(DimensUnit.X3LARGE))
fun Modifier.paddingEndX4Large() = this.padding(end = convertUnitToPadding(DimensUnit.X4LARGE))
fun Modifier.noEndPadding() = this.padding(end = 0.dp)

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
