package el.dv.dvproperties.core.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import el.dv.domain.logging.AppLog
import el.dv.presentation.view.effect.ViewEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DVPropertiesViewModel : ViewModel() {
    private val eventChannel = Channel<DVPropertiesViewEvent>(Channel.UNLIMITED)

    private val internalViewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> = internalViewEffect

    init {
        eventChannel
            .consumeAsFlow()
            .catch { error ->
                AppLog.d(TAG, "event ${error.javaClass.simpleName}")
            }
            .onEach { event ->
                AppLog.d(TAG, "event: ${event.javaClass.simpleName}")
                when (event) {
                    is DVPropertiesViewEvent.Init -> handleInitEvent(event)
                }
            }.launchIn(viewModelScope)
    }

    fun handleEvent(event: DVPropertiesViewEvent) {
        eventChannel.trySend(event)
    }

    private fun sendViewEffect(viewEffect: ViewEffect) {
        internalViewEffect.value = viewEffect
    }

    private fun handleInitEvent(event: DVPropertiesViewEvent.Init) {
        AppLog.d(TAG, "handleInitEvent")
    }

    companion object {
        const val TAG = "DVPropertiesViewModel"
    }
}

sealed class DVPropertiesViewEvent {
    object Init : DVPropertiesViewEvent()
}
