package el.dv.dvpropertiesdata.extension

import el.dv.domain.core.Result
import el.dv.dvpropertiesdata.R
import retrofit2.Response

fun String.appendUrl(): String {
    return this.plus("&locale=us&limit=1&key=${R.string.grasshopper_key}")
}

inline fun <reified T> Response<T>.ifSuccess(): T? {
    if (this.isSuccessful) {
        if (this.body() != null) {
            return this.body()
        }
    }
    return null
}

inline fun <reified T> Response<T>.ifSuccess(crossinline action: (data: T) -> Unit): Response<T> {
    if (this.isSuccessful) {
        if (this.body() != null) {
            action.invoke(requireNotNull(this.body()))
        }
    }
    return this
}
