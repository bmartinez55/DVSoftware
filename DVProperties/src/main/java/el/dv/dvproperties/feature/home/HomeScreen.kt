package el.dv.dvproperties.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import el.dv.compose_uikit.theme.dvpropeerties.DVPropertiesTheme
import el.dv.domain.dvproperties.PropertyDetails

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PriorityHorizontalGrid(
    propertyList: List<PropertyDetails>,
    pageCount: Int = 10,
    pagerState: PagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) { propertyList.size }
) {
    HorizontalPager(
        modifier = Modifier,
        state = pagerState,
        pageSpacing = 0.dp,
        userScrollEnabled = true,
        reverseLayout = false,
        contentPadding = PaddingValues(0.dp),
        beyondBoundsPageCount = 0,
        pageSize = PageSize.Fill,
        flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
        key = null,
        pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(Orientation.Horizontal)
    ) { page ->

    }
}

@Composable
private fun GridItem(
    propertyDetails: PropertyDetails
) {

}

@Preview(
    name = "GridItemPreview",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL
)
@Composable
fun GridItemPreview() {
    DVPropertiesTheme {
        GridItem(PropertyDetails())
    }
}
