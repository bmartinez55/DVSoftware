package el.dv.domain.navigation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class MapVisualType : Parcelable {
    Normal,
    Hybrid,
    Satellite,
    Terrain,
    ThreeDimension
}

data class MapConfigurations(
    val mapFeature: NavigationMapFeature = NavigationMapFeature(),
    val mapVisualType: MapVisualType = MapVisualType.Normal
) {
    fun isIn3DMode(): Boolean = this.mapVisualType == MapVisualType.ThreeDimension
}
