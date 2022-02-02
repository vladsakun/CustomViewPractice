package com.jungle.android.customviewpractice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes

/**
 * Created by Vlad Sakun on 12/28/2021
 */
class EmotionalFaceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_FACE_COLOR = Color.YELLOW
        private const val DEFAULT_EYES_COLOR = Color.BLACK
        private const val DEFAULT_MOUTH_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_WIDTH = 4.0f

        const val HAPPY = 0L
        const val SAD = 1L
    }

    private var faceColor = DEFAULT_FACE_COLOR
    private var eyesColor = DEFAULT_EYES_COLOR
    private var mouthColor = DEFAULT_MOUTH_COLOR
    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = DEFAULT_BORDER_WIDTH

    private val paint = Paint()
    private val mouthPath = Path()

    // View size in pixels
    private var size = 0

    var happinessState = HAPPY
        set(state) {
            field = state

            invalidate()
        }

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs, defStyleAttr)
    }

    private fun setupAttributes(attrs: AttributeSet?, defStyleAttr: Int){
        context.withStyledAttributes(attrs, R.styleable.EmotionalFaceView, defStyleAttr){
            happinessState = getInt(R.styleable.EmotionalFaceView_state, HAPPY.toInt()).toLong()
            faceColor = getColor(R.styleable.EmotionalFaceView_faceColor, DEFAULT_FACE_COLOR)
            eyesColor = getColor(R.styleable.EmotionalFaceView_eyesColor, DEFAULT_EYES_COLOR)
            mouthColor = getColor(R.styleable.EmotionalFaceView_mouthColor, DEFAULT_MOUTH_COLOR)
            borderColor = getColor(R.styleable.EmotionalFaceView_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = getDimension(R.styleable.EmotionalFaceView_borderWidth, DEFAULT_BORDER_WIDTH)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
    }

    private fun drawMouth(canvas: Canvas?) {
        mouthPath.moveTo(size * 0.22f, size * 0.70f)

        mouthPath.quadTo(size * 0.50f, size * 0.80f, size * 0.78f, size * 0.70f)

        mouthPath.quadTo(size * 0.50f, size * 0.90f, size * 0.22f, size * 0.70f)

        paint.color = mouthColor
        paint.style = Paint.Style.FILL

        canvas?.drawPath(mouthPath, paint)
    }

    private fun drawEyes(canvas: Canvas?) {
        paint.color = eyesColor
        paint.style = Paint.Style.FILL

        val leftEyeRect = RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f)

        canvas?.drawOval(leftEyeRect, paint)

        val rightEyeRect = RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f)

        canvas?.drawOval(rightEyeRect, paint)
    }

    private fun drawFaceBackground(canvas: Canvas?) {
        paint.color = faceColor
        paint.style = Paint.Style.FILL

        val radius = size / 2f

        canvas?.drawCircle(size / 2f, size / 2f, radius, paint)

        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth

        canvas?.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = Math.min(measuredWidth, measuredHeight)

        setMeasuredDimension(size, size)
    }
}