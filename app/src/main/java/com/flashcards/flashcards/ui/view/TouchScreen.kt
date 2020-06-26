package com.flashcards.flashcards.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlin.math.ceil

class TouchScreen : View {

    private var statusListener: TouchStatusListener? = null
    private val paint = Paint()
    private var cellSize: Int = 0

    private val pointerLocation = ArrayList<Point>()

    private var mHandler: Handler = Handler()

    interface TouchStatusListener {
        fun onCompleted(result: Boolean)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    init {
        cellSize = (Cell.SIZE * resources.displayMetrics.density).toInt()

        paint.style = Paint.Style.STROKE
        paint.color = Color.WHITE
        paint.strokeWidth = 2f

        val maxPointers = 100
        for (i in 0 until maxPointers) {
            pointerLocation.add(Point())
        }
    }

    fun setStatusListener(listener: TouchStatusListener) {
        statusListener = listener
    }

    fun release() {
        statusListener = null
        mHandler.removeCallbacksAndMessages(null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        generateCells()
        invalidate()
    }

    private fun generateCells() {
        rows = height / cellSize
        rowsize = 1f * height / rows

        cols = width / cellSize
        colsize = 1f * width / cols

        var x1: Float
        var y1: Float
        var x2: Float
        var y2 = 0f
        var id: Int

        listCells.clear()

        for (i in 0 until rows) {
            y1 = y2
            y2 = rowsize * (i + 1)
            x2 = 0f
            for (j in 0 until cols) {
                x1 = x2
                x2 = colsize * (j + 1)
                id = i * cols + j
                val cell = Cell(id, iceil(x1), iceil(y1), iceil(x2), iceil(y2))
                listCells.add(cell)
            }
        }
    }

    private fun iceil(n: Float) = ceil(n).toInt()

    private var rows: Int = 0
    private var cols: Int = 0
    private var rowsize: Float = 0f
    private var colsize: Float = 0f

    private val listCells : MutableList<Cell> = arrayListOf()

    private data class Cell(
        val id: Int,
        val x1: Int,
        val y1: Int,
        val x2: Int,
        val y2: Int,

        var selected: Boolean = false
    ) {
        fun contain(x: Int, y: Int): Boolean {
            return x in x1..x2 && y in y1..y2
        }

        companion object {
            const val SIZE = 120
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        listCells.forEach {
            if (it.selected) {
                paint.color = Color.GREEN
            } else {
                paint.color = Color.GRAY
            }
            canvas.drawRect(it.x1.toFloat(), it.y1.toFloat(), it.x2.toFloat(), it.y2.toFloat(), paint)
        }

        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        for (i in 0..rows) {
            val y = iceil(rowsize*i)
            canvas.drawLine(0f, y.toFloat(), height.toFloat(), y.toFloat(), paint)
        }
        for (i in 0..cols) {
            val x = iceil(colsize * i)
            canvas.drawLine(x.toFloat(), 0f, x.toFloat(), height.toFloat(), paint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_MOVE,
                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP -> {
                val x = event.x.toInt()
                val y = event.y.toInt()
                listCells.forEach {
                    if (it.contain(x, y)) {
                        it.selected = true
                    }
                }

                if (listCells.all { it.selected }) {
//                    mHandler.postDelayed({
//                        statusListener
//                    })
                    Toast.makeText(context, "All block are clicked.", Toast.LENGTH_LONG).show()
                }
            }
        }

        invalidate()
        return true
    }
}
