package el.dv.domain.extension

import kotlinx.coroutines.channels.SendChannel

fun String?.isNotNullAndNotEmpty(): Boolean {
    return this != null && this != ""
}

fun <E> SendChannel<E>.offerWhenOpen(data: E) {
    when (this.isClosedForSend) {
        false -> this.trySend(data)
        else -> {}
    }
}
