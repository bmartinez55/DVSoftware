package el.dv.presentation.extension

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import el.dv.domain.core.Geolocation
import kotlinx.coroutines.channels.SendChannel

typealias ActionListener = () -> Unit

fun Geolocation.toGoogleLatLng(): LatLng {
    return LatLng(this.lat, this.lon)
}

fun LatLng.toGeolocation(): Geolocation {
    return Geolocation(lat = this.latitude, lon = this.longitude)
}

fun Location.toGeoLocation(): Geolocation {
    return Geolocation(lat = this.latitude, lon = this.longitude, accuracy = this.accuracy)
}

fun Fragment.onBackPress(enabled: Boolean, onBackPressAction: () -> Unit) {
    this.activity?.onBackPressedDispatcher?.addCallback(
        this.viewLifecycleOwner,
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                onBackPressAction()
            }
        }
    )
}

fun <E> SendChannel<E>.offerWhenOpen(data: E) {
    when (this.isClosedForSend) {
        false -> this.trySend(data)
        else -> {}
    }
}

fun <K, V> Map<K, V>.addOrUpdate(key: K, value: V): Map<K, V> {
    return this.toMutableMap().apply {
        this[key] = value
    }.toMap()
}

fun <T : Parcelable> T.convertParcelableToBundle(key: String): Bundle {
    return Bundle().apply {
        putParcelable(key, this@convertParcelableToBundle)
    }
}

fun <T : Any> Bundle.getBundleData(key: String, defaultValue: T): T {
    return when (this.containsKey(key)) {
        true -> this.getParcelable(key) ?: defaultValue
        false -> defaultValue
    }
}

fun <T : Any> Fragment.getBundleData(key: String, defaultValue: T) = lazy {
    arguments?.getBundleData(key, defaultValue) ?: defaultValue
}

fun Fragment.requireContentView(
    compositionStrategy: ViewCompositionStrategy = ViewCompositionStrategy.DisposeOnDetachedFromWindow,
    context: Context = requireContext(),
    content: @Composable () -> Unit
): ComposeView {
    val view = ComposeView(context)
    view.setViewCompositionStrategy(compositionStrategy)
    view.setContent(content)
    return view
}

fun Fragment.contentView(
    compositionStrategy: ViewCompositionStrategy = ViewCompositionStrategy.DisposeOnDetachedFromWindow,
    context: Context? = getContext(),
    content: @Composable () -> Unit
): ComposeView? {
    context ?: return null
    val view = ComposeView(context)
    view.setViewCompositionStrategy(compositionStrategy)
    view.setContent(content)
    return view
}
