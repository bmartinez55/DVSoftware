package el.dv.dvproperties.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import el.dv.domain.logging.AppLog
import el.dv.presentation.view.effect.ViewEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel : ViewModel() {

    private val internalState = MutableLiveData(InternalState())
    private val state: InternalState
        get() = requireNotNull(internalState.value)

    val viewState: LiveData<HomeViewState> = internalState.map { it.viewState }.distinctUntilChanged()

    private val internalViewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> = internalViewEffect

    private val eventChannel = Channel<HomeViewEvent>(Channel.UNLIMITED)

    init {
        eventChannel
            .consumeAsFlow()
            .catch { e -> AppLog.d("Event catch $e") }
            .onEach { event ->
                when (event) {
                    is HomeViewEvent.Init -> handleInit(event)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun handleInit(event: HomeViewEvent.Init) {
        AppLog.d(TAG, "handleInit")

    }

    data class InternalState(
        val viewState: HomeViewState = HomeViewState()
    )

    companion object {
        const val TAG = "HomeViewModel"
    }
}
