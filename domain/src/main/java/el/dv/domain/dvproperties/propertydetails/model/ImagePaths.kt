package el.dv.domain.dvproperties.propertydetails.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImagePaths(val imagePathsList: List<String> = emptyList()): Parcelable
