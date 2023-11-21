package el.dv.fayucafinder.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import el.dv.compose.theme.fayucafinder.FayucaFinderTheme
import el.dv.domain.navigation.model.MapVisualType

fun Fragment.inflateComposeContainer(view: ComposeView, content: @Composable () -> Unit) {
    view.apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            FayucaFinderTheme {
                content.invoke()
            }
        }
    }
}

fun String.toMapVisualType(): MapVisualType {
    return when (this) {
        MapVisualType.Normal.name -> MapVisualType.Normal
        MapVisualType.Satellite.name -> MapVisualType.Satellite
        MapVisualType.Terrain.name -> MapVisualType.Terrain
        MapVisualType.Hybrid.name -> MapVisualType.Hybrid
        MapVisualType.ThreeDimension.name -> MapVisualType.ThreeDimension
        else -> MapVisualType.Normal
    }
}
