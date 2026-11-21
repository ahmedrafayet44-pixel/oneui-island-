package com.rafayet.oneuiisland.music

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.view.View
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import kotlin.math.sin

class EqualizerView(context: Context) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        strokeWidth = 5f
        strokeCap = Paint.Cap.ROUND
    }

    private val bars = 4
    private var progress = 0f

    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 700
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()

        addUpdateListener {
            progress = it.animatedValue as Float
            invalidate()
        }

    }
    override fun onDetachedFromWindow() {
    animator.cancel()
    super.onDetachedFromWindow()
    }
fun startAnimation() {
    if (!animator.isStarted) {
        animator.start()
    }
}

fun stopAnimation() {
    animator.cancel()
    progress = 0f
    invalidate()
}
        override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerY = height / 2f
        val spacing = width / (bars + 1).toFloat()

        for (i in 0 until bars) {
            val wave = sin((progress * 6.28f + i * 1.5f).toDouble()).toFloat()
            val barHeight = 8f + ((wave + 1f) * 13f)

            val x = spacing * (i + 1)

            canvas.drawLine(
                x,
                centerY - barHeight,
                x,
                centerY + barHeight,
                paint
            )
        }
    }
}
