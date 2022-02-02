package com.jungle.android.customviewpractice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by Vlad Sakun on 1/5/2022
 */
class AcceleratorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    private var radius = 0.0f // Radius for semicircle
    private var strokeWidth = 20f
    private var semicircleColor = Color.GRAY
    private var strokeColor = Color.BLACK
    private var coreColor = Color.BLUE
    private var lineColor: Int = Color.GREEN
    private val oval = RectF()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (kotlin.math.min(width, height) / 2.0 * 0.8).toFloat()
        val diameter = kotlin.math.min(width, height).toFloat()
        oval.set(0f + strokeWidth, 0f + strokeWidth, diameter - strokeWidth, diameter - strokeWidth)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.reset()
        paint.color = semicircleColor
        canvas?.drawArc(oval, 180f, 180f, true, paint)

        paint.color = strokeColor
        paint.strokeWidth = strokeWidth
        paint.style = Paint.Style.STROKE
        canvas?.drawArc(oval, 180f, 180f, true, paint)
        paint.reset()
    }
}