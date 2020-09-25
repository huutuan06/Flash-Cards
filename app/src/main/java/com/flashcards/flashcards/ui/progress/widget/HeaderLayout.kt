package com.flashcards.flashcards.ui.progress.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.flashcards.flashcards.R
import kotlin.math.max
import kotlin.math.min

class HeaderLayout : ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        private const val ANIMATION_DURATION = 500L
    }

    /**
     * This class contains values to draw success/fail portion.
     * Progress is drawn from startX to currentX.
     * Animation is played from currentX to endX.
     */
    private data class Portion(
        var startX: Float,
        var currentX: Float,
        var endX: Float,
        @ColorInt var color: Int
    )

    private var totalCount: Int = 0
    private var successCount: Int = 0
    private var failCount: Int = 0

    private val paint = Paint().apply {
        style = Paint.Style.FILL
    }
    private var rect = RectF(0f, 0f, 0f, 0f)

    private val cornerRadius = context.resources.getDimension(R.dimen.radius_item_button)

    private lateinit var mBitmap: Bitmap
    private lateinit var mCanvas: Canvas

    // To clear the residual to make the background round-rectangle
    private lateinit var mMaskBitmap: Bitmap
    private lateinit var mMaskCanvas: Canvas
    private val mMaskPorterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    private var needRedrawMask = true

    private val colorNeutral: Int = ContextCompat.getColor(context, R.color.color_neutral)
    private val colorFail: Int = ContextCompat.getColor(context, R.color.color_fail)
    private val colorSuccess: Int = ContextCompat.getColor(context, R.color.color_success)
    private val colorWhite: Int = ContextCompat.getColor(context, R.color.white)

    // Success portion is animated from left to right.
    private val successPortion = Portion(0f, 0f, 0f, colorSuccess)
    // Fail portion is animated from right to left.
    private val failPortion = Portion(0f, 0f, 0f, colorFail)

    private var animatorSuccess: ValueAnimator? = null
    private var animatorFail: ValueAnimator? = null

    init {
        setWillNotDraw(false)
        setLayerType(LAYER_TYPE_HARDWARE, null)

        createCanvas()
        resetAnimationValues()
    }

    fun setValues(totalCount: Int, successCount: Int, failCount: Int) {
        this.totalCount = totalCount
        this.successCount = successCount
        this.failCount = failCount

        successPortion.apply {
            endX = getSuccessPortionX()
        }
        failPortion.apply {
            endX = getFailPortionX()
        }

        animateProgress()

        invalidate()
    }

    //region animation
    private fun animateProgress() {
        animatorSuccess = createValueAnimator(successPortion)
        animatorSuccess?.start()

        animatorFail = createValueAnimator(failPortion)
        animatorFail?.start()
    }

    private fun endAnimation() {
        animatorSuccess?.end()
        animatorFail?.end()
        animatorSuccess = null
        animatorFail = null
    }

    private fun createValueAnimator(portion: Portion): ValueAnimator? {
        return if (portion.currentX != portion.endX) {
            ValueAnimator.ofFloat(portion.currentX, portion.endX)
        } else {
            null
        }?.apply {
            duration = ANIMATION_DURATION
            addUpdateListener {
                portion.currentX = it.animatedValue as Float
                invalidate()
            }
        }
    }

    private fun resetAnimationValues() {
        val successProgress = getSuccessPortionX()
        successPortion.apply {
            startX = 0f
            currentX = successProgress
            endX = successProgress
        }

        val failProgress = getFailPortionX()
        failPortion.apply {
            startX = width.toFloat()
            currentX = failProgress
            endX = failProgress
        }
    }

    private fun getSuccessPortionX() =
        if (totalCount > 0) {
            width.toFloat() * successCount / totalCount
        } else {
            0f
        }
    private fun getFailPortionX() =
        if (totalCount > 0) {
            width * (1 - failCount / totalCount.toFloat())
        } else {
            width.toFloat()
        }
    //endregion

    private fun createCanvas() {
        if (width > 0 && height > 0) {
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            mCanvas = Canvas(mBitmap)

            mMaskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            mMaskCanvas = Canvas(mMaskBitmap)

            needRedrawMask = true
        }
    }

    override fun onDetachedFromWindow() {
        endAnimation()
        super.onDetachedFromWindow()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        createCanvas()
        resetAnimationValues()
    }

    override fun onDraw(canvas: Canvas) {
        drawPortions()
        if (needRedrawMask) {
            drawMask()
            needRedrawMask = false
        }

        canvas.drawBitmap(mBitmap, 0f, 0f, null)

        paint.apply {
            xfermode = mMaskPorterDuffXfermode
        }
        canvas.drawBitmap(mMaskBitmap, 0f, 0f, paint)
    }

    private fun drawPortions() {
        val w = width.toFloat()
        val h = height.toFloat()

        rect.bottom = h

        // Draw background
        paint.apply {
            color = colorNeutral
            style = Paint.Style.FILL
            xfermode = null
        }
        rect.apply {
            left = 0f
            right = w
        }
        mCanvas.drawRect(rect, paint)

        drawPortion(successPortion)
        drawPortion(failPortion)
    }

    private fun drawPortion(portion: Portion) {
        val startX = portion.startX
        val endX = portion.currentX
        if (startX != endX) {
            paint.apply {
                color = portion.color
                style = Paint.Style.FILL
                xfermode = null
            }
            rect.apply {
                left = min(startX, endX)
                right = max(startX, endX)
            }
            mCanvas.drawRect(rect, paint)
        }
    }

    private fun drawMask() {
        // Draw background round-rectangle
        paint.apply {
            paint.color = colorWhite
            paint.style = Paint.Style.FILL
        }
        rect.apply {
            left = 0f
            right = width.toFloat()
            top = 0f
            bottom = height.toFloat()
        }
        mMaskCanvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
    }
}
