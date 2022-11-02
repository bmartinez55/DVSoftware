package el.dv.presentation.extension

import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation

fun View.postSlideUpFromBottomAnimation(
    duration: Long,
    fillBefore: Boolean = false,
    fillAfter: Boolean = false,
    isFillEnabled: Boolean = false,
    repeatMode: Int = TranslateAnimation.ABSOLUTE,
    repeatCount: Int = 0,
    startOffSet: Long = 0L,
    animationStart: () -> Unit = {},
    animationEnd: () -> Unit = {},
    animationRepeat: () -> Unit = {}
) {
    this.post {
        val animation = TranslateAnimation(0F, 0F, this.height.toFloat(), 0F)
        animation.duration = duration
        animation.fillBefore = fillBefore
        animation.fillAfter = fillAfter
        animation.isFillEnabled = isFillEnabled
        animation.repeatMode = repeatMode
        animation.repeatCount = repeatCount
        animation.startOffset = startOffSet
        this.startAnimation(animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                animationStart()
            }

            override fun onAnimationEnd(animation: Animation?) {
                animationEnd()
            }

            override fun onAnimationRepeat(animation: Animation?) {
                animationRepeat()
            }
        })
    }
}
