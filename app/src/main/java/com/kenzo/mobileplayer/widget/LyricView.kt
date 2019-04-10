package com.kenzo.mobileplayer.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.itheima.player.model.LyricBean
import com.kenzo.mobileplayer.R
import com.kenzo.mobileplayer.util.LyricLoader
import com.kenzo.mobileplayer.util.LyricUtil
import org.jetbrains.anko.doAsync

class LyricView : View {
    val list by lazy { ArrayList<LyricBean>() }
    var viewW: Int = 0
    var viewH: Int = 0
    var centerLine = 0
    var bigSize: Float = 0f
    var smallSize: Float = 0f
    var white = 0
    var green = 0
    var lineHeight = 0
    var duration = 0
    var progress = 0
    var updateByPro = true   //指定是否可以通过进度更新歌词
    var downY = 0f     //手指按下时的Y
    var offsetY = 0f
    var markY = 0f

    val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        bigSize = resources.getDimension(R.dimen.bigSize)
        smallSize = resources.getDimension(R.dimen.smallSize)
        white = resources.getColor(R.color.white)
        green = resources.getColor(R.color.green)
        paint.textAlign = Paint.Align.CENTER
        lineHeight = resources.getDimensionPixelOffset(R.dimen.lineHeight)

//        for (i in 0 until 30) {
////            list.add(LyricBean(2000 * i, "在播放第${i}行歌词"))
////        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (list.size == 0) {
            drawSingleLine(canvas)
        } else {
            drawMutieLine(canvas)
        }


    }

    private fun drawMutieLine(canvas: Canvas?) {
        if (updateByPro) {
            //求居中行偏移量
            var lineTime = 0  //行可用时间
            if (centerLine == list.size - 1) {
                lineTime = duration - list.get(centerLine).startTime

            } else {
                val centerS = list.get(centerLine).startTime
                val nextS = list.get(centerLine + 1).startTime
                lineTime = nextS - centerS
            }

            val offsetTime = progress - list.get(centerLine).startTime   //偏移时间
            val offsetPercent = offsetTime / (lineTime.toFloat())     //偏移百分比
            offsetY = lineHeight * offsetPercent
        }


        val centerText = list.get(centerLine).content
        val bounds = Rect()
        paint.getTextBounds(centerText, 0, centerText.length, bounds)
        val textH = bounds.height()
        val centerY = viewH / 2 - textH / 2 - offsetY
        for ((index, value) in list.withIndex()) {
            if (index == centerLine) {
                paint.color = green
                paint.textSize = bigSize
            } else {
                paint.color = white
                paint.textSize = smallSize
            }
            val curX = viewW / 2
            val curY = centerY + (index - centerLine) * lineHeight

            if (curY < 0) continue
            if (curY > viewH + textH) break

            val cutText = list.get(index).content
            canvas?.drawText(cutText, curX.toFloat(), curY.toFloat(), paint)
        }
    }

    private fun drawSingleLine(canvas: Canvas?) {
        val text = "正在加载歌词..."

        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        val textW = bounds.width()
        val textH = bounds.height()
        //val x = viewW/2 - textW/2
        val y = viewH / 2 - textH / 2
        canvas?.drawText(text, viewW / 2.toFloat(), y.toFloat(), paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewH = h
        viewW = w
    }

    /**
     * 传递当前播放进度，实现歌词播放
     */
    fun uodateProgress(progress: Int) {
        if (!updateByPro) return


        if (list.size == 0) return

        this.progress = progress
        //获取居中行行号
        if (progress >= list.get(list.size - 1).startTime) {
            centerLine = list.size - 1
        } else {
            for (index in 0 until list.size - 1) {
                if (progress >= list.get(index).startTime && progress < list.get(index + 1).startTime) {
                    centerLine = index
                    break
                }
            }
        }
        invalidate()
    }

    fun setSongDuration(duration: Int) {
        this.duration = duration

    }

    fun setSongName(name: String) {
        doAsync {
            this@LyricView.list.clear()
            this@LyricView.list.addAll(LyricUtil.parseLyric(LyricLoader.loadLyricFile(name)))
        }

    }

    /**
     * 歌词控件手势事件处理
     * 手指按下时 停止通过进度更新歌词
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    updateByPro = false
                    //手指按下时的Y值
                    downY = event.y
                    markY = this.offsetY
                }
                MotionEvent.ACTION_UP -> updateByPro = true
                MotionEvent.ACTION_MOVE -> {
                    //手指当前位置的Y值
                    val endY = event.y
                    //手指移动的Y值
                    val offY = downY - endY
                    //重新设置居中行偏移量
                    this.offsetY = offY + markY
                    //判断最终偏移量与行高的关系  重新确定居中行
                    if (Math.abs(this.offsetY) >= lineHeight) {
                        val offsetLine = (this.offsetY / lineHeight).toInt()
                        centerLine += offsetLine
                        //对居中行做边界处理
                        if (centerLine < 0) {
                            centerLine = 0
                        } else if (centerLine >= list.size) {
                            centerLine = list.size - 1
                        }

                        //重置downY
                        this.downY = endY
                        //重新确定偏移量Y
                        this.offsetY %= lineHeight
                        //重置偏移量marky
                        this.markY = this.offsetY

                        //更新播放进度
                        listener?.let {
                            it(list.get(centerLine).startTime)
                        }
                    }
                    invalidate()
                }
            }

        }


        return true
    }

   //进度回调函数
    private var listener:((progress: Int)-> Unit)? = null
    //设置进度回调函数
    fun setProgressListener(listener:(progress: Int)-> Unit){
        this.listener = listener
    }
}