package el.dv.fayucafinder.feature.map

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import el.dv.domain.logging.AppLog
import el.dv.presentation.extension.OnAction

class FayucaFinderMapLifecycleObserver(
    private val lifecycleOwner: LifecycleOwner,
    private val onResumeAction: OnAction,
    private val onPauseAction: OnAction,
) : DefaultLifecycleObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                AppLog.d(TAG, "onStart")
            }

            override fun onResume(owner: LifecycleOwner) {
                AppLog.d(TAG, "onResume")
                onResumeAction()
            }

            override fun onPause(owner: LifecycleOwner) {
                AppLog.d(TAG, "onPause")
                onPauseAction()
            }

            override fun onStop(owner: LifecycleOwner) {
                AppLog.d(TAG, "onStop")
            }
        })
    }

    companion object {
        const val TAG = "FayucaFinderMapLifecycleObserver"
    }
}
