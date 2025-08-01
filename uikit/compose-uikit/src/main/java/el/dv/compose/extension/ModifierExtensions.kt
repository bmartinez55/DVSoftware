package el.dv.compose.extension

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Calculates the gradient angle and draws on the canvas from the receiver
 */
fun Modifier.angledGradientBackground(
    colors: List<Color>,
    angle: Float,
    durationMillis: Int = 3000
): Modifier = composed {
    // Duplicate the first color at the end to create a seamless loop
    val loopedColors = colors + colors.first()

    val infiniteTransition = rememberInfiniteTransition(label = "gradientAnimation")
    val animatedColors = loopedColors.mapIndexed { index, color ->
        if (index < loopedColors.size - 1) {
            val nextColor = loopedColors[index + 1]
            infiniteTransition.animateColor(
                initialValue = color,
                targetValue = nextColor,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "colorAnimation$index"
            )
        } else {
            null // Skip the last color to avoid redundant animation
        }
    }.filterNotNull()

    drawBehind {
        val brush = Brush.linearGradient(
            colors = animatedColors.map { it.value },
            start = Offset(0f, 0f),
            end = Offset(size.width, size.height)
        )
        drawRect(brush)
    }
}
//    Modifier.drawBehind {
//
//        // Set the angle of the gradient in radians
//        val angleRadius = (angle.div(180f)).times(PI)
//
//        // Calculate the X with cosine
//        val x = kotlin.math.cos(angleRadius).toFloat()
//
//        // Calculate the Y with sine
//        val y = kotlin.math.sin(angleRadius).toFloat()
//
//        // Calculate the radius
//        val radius = sqrt(size.width.pow(2).plus(size.height.pow(2))).div(2f)
//
//        // Calculate the approximate offset
//        val offset = center.plus(Offset(x.times(radius), y.times(radius)))
//
//        // Calculate the exact offset
//        val exactOffset = Offset(
//            x = kotlin.math.min(offset.x.coerceAtLeast(0f), size.width),
//            y = size.height.minus(kotlin.math.min(offset.y.coerceAtLeast(0f), size.height))
//        )
//
//        drawRect(
//            brush = Brush.linearGradient(
//                colors = colors,
//                start = Offset(size.width, size.height).minus(exactOffset),
//                end = exactOffset
//            ),
//            size = size
//        )
//    }
// ) }

/**
 * Chain operation to current Modifier
 */
inline fun Modifier.ifThen(condition: Boolean, otherModifier: () -> Modifier): Modifier = if (condition) {
    this.then(otherModifier.invoke())
} else {
    this
}
