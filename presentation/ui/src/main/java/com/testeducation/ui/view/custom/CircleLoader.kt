package com.testeducation.ui.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import com.testeducation.ui.R
import kotlin.math.cos
import kotlin.math.sin

class CircleLoader@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var secondPaint: Paint

    private val rotateAnimation by lazy {
        RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            duration = 2000L
            interpolator = LinearInterpolator()
            repeatCount = Animation.INFINITE
        }
    }

    private val listColor: List<Int> = listOf(
        context.getColor(R.color.colorDarkGreen),
        context.getColor(R.color.colorBlue),
        context.getColor(R.color.colorRed),
        context.getColor(R.color.colorOrange)
    )

    init {
        startAnimation()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        secondPaint = Paint()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        secondPaint.apply {
            color = context.getColor(R.color.colorDarkBlue)
        }

        canvas?.let {
            val startAngle = -Math.PI / 2f
            val centerX = width / 2
            val centerY = height / 2
            val circlesRadius = 18f
            val circlesCount = listColor.size
            for (i in 0 until circlesCount) {
                val angle = if (i == 0) startAngle
                else startAngle + (i * ((2 * Math.PI) / circlesCount))

                val x = centerX + cos(angle) * (width / 2f - circlesRadius)
                val y = centerY + sin(angle) * (width / 2f - circlesRadius)
                secondPaint.color = listColor.getOrElse(i) {
                    context.getColor(R.color.colorDarkGreen)
                }
                canvas.drawCircle(x.toFloat(), y.toFloat(), circlesRadius, secondPaint)
            }
        }
    }

    private fun startAnimation() {
        startAnimation(rotateAnimation)
    }

    fun setVisibility(isVisible: Boolean) {
        visibility = if (isVisible) {
            startAnimation(rotateAnimation)
            VISIBLE
        } else {
            rotateAnimation.cancel()
            GONE
        }
    }
}