package com.jungle.android.customviewpractice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by Vlad Sakun on 12/28/2021
 */
class AndroidView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttrs: Int = 0
) : View(context, attributeSet, defAttrs) {

    companion object {
        private const val OFFSET_BETWEEN_BODY_PARTS = 0.02f
    }

    private var paint = Paint()
    private val mMatrix = Matrix()
    private var path = Path()
    private var greenColor = Color.parseColor("#a4c639")
    private var eyesColor = Color.WHITE
    val cornerRadius = 80f
    private val bottomCorners = floatArrayOf(
        0f, 0f,
        0f, 0f,
        cornerRadius, cornerRadius,
        cornerRadius, cornerRadius
    )

    private val armsCorners = floatArrayOf(
        cornerRadius, cornerRadius,
        cornerRadius, cornerRadius,
        cornerRadius, cornerRadius,
        cornerRadius, cornerRadius
    )

    private var size = 0

    init {
        initPaint()
    }

    private fun initPaint() {
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = greenColor
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawBody(canvas)
        drawHead(canvas)
    }

    private fun drawBody(canvas: Canvas?) {
        path.reset()

        val rectangle = RectF(size * 0.30f, size * 0.30f, size * 0.70f, size * 0.70f)
        path.addRoundRect(rectangle, bottomCorners, Path.Direction.CW)
        canvas?.drawPath(path, paint)

        drawLegs(canvas)
        drawArms(canvas)
    }

    private fun drawArms(canvas: Canvas?) {
        path.reset()
        val leftArm = RectF(size * 0.20f, size * 0.30f, size * 0.28f, size * 0.60f)
        path.addRoundRect(leftArm, armsCorners, Path.Direction.CW)
        canvas?.drawPath(path, paint)

        path.reset()
        val rightArm = RectF(size * 0.72f, size * 0.30f, size * 0.80f, size * 0.60f)
        path.addRoundRect(rightArm, armsCorners, Path.Direction.CW)
        canvas?.drawPath(path, paint)
    }

    private fun drawLegs(canvas: Canvas?) {
        path.reset()
        val leftLeg = RectF(size * 0.38f, size * 0.60f, size * 0.48f, size * 0.85f)
        path.addRoundRect(leftLeg, bottomCorners, Path.Direction.CW)
        canvas?.drawPath(path, paint)

        path.reset()
        val rightLeg = RectF(size * 0.52f, size * 0.60f, size * 0.62f, size * 0.85f)
        path.addRoundRect(rightLeg, bottomCorners, Path.Direction.CW)
        canvas?.drawPath(path, paint)
    }

    private fun drawHead(canvas: Canvas?) {
        paint.color = greenColor

        val rect = RectF(
            size * 0.30f,
            size * 0.12f,
            size * 0.70f,
            size * 0.44f
        )
        path.reset()
        path.arcTo(rect, 180f, 180f, true)
        canvas?.drawPath(path, paint)

        mMatrix.reset()
        path.reset()
        val leftEar = RectF(size * 0.35f, size * 0.06f, size * 0.37f, size * 0.17f)
        path.addRoundRect(leftEar, bottomCorners, Path.Direction.CW)
        mMatrix.postRotate(145f, leftEar.centerX(), leftEar.centerY())
        path.transform(mMatrix)
        canvas?.drawPath(path, paint)

        mMatrix.reset()
        path.reset()
        val rightEar = RectF(size * 0.63f, size * 0.06f, size * 0.65f, size * 0.16f)
        path.addRoundRect(rightEar, bottomCorners, Path.Direction.CW)
        mMatrix.postRotate(215f, rightEar.centerX(), rightEar.centerY())
        path.transform(mMatrix)
        canvas?.drawPath(path, paint)

        paint.color = eyesColor
        canvas?.drawCircle(size * 0.41f, size * 0.21f, size * 0.015f, paint)
        canvas?.drawCircle(size * 0.59f, size * 0.21f, size * 0.015f, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = Math.min(measuredWidth, measuredHeight)

        setMeasuredDimension(size, size)
    }
}