package el.dv.domain.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatchers {
    val IO: CoroutineDispatcher
    val Main: CoroutineDispatcher
    val DEFAULT: CoroutineDispatcher
}

class AppCoroutineDispatchers : CoroutineDispatchers {
    override val IO: CoroutineDispatcher = Dispatchers.IO
    override val Main: CoroutineDispatcher = Dispatchers.Main
    override val DEFAULT: CoroutineDispatcher = Dispatchers.Default
}
