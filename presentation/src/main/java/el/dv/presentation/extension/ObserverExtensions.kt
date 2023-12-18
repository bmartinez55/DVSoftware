package el.dv.presentation.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import el.dv.presentation.view.effect.ViewEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun ObserveViewEffects(flow: Flow<ViewEffect>, onViewEffect: (ViewEffect) -> Unit) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifeCycleOwner.lifecycle) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(onViewEffect)
        }
    }
}
