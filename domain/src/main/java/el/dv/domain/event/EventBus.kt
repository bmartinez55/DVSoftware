package el.dv.domain.event

import android.content.Intent
import el.dv.domain.logging.AppLog
import el.dv.domain.navigation.model.MapVisualType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

interface EventBus {
    val events: SharedFlow<AppEvent>
    suspend fun publishEvents(event: AppEvent)
}

class EventBusImpl : EventBus {
    private val internalEvents = MutableSharedFlow<AppEvent>()
    override val events: SharedFlow<AppEvent> = internalEvents

    override suspend fun publishEvents(event: AppEvent) {
        AppLog.d(TAG, "publishEvents ${event.javaClass.name}")
        try {
            internalEvents.emit(event)
        } catch (e: Exception) {
            AppLog.e(TAG, "publishEvent error", e)
        }
    }

    companion object {
        const val TAG = "EventBusImpl"
    }
}

sealed class AppEvent {
    data class MapVisualTypeChangedReceived(val mapVisualType: MapVisualType) : AppEvent()
    data class IntentReceived(val intent: Intent) : AppEvent()
}
