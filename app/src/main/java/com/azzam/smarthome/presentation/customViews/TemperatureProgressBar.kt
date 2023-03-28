package com.azzam.smarthome.presentation.customViews

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.azzam.smarthome.R
import com.azzam.smarthome.databinding.LayoutTemperatureProgressBarBinding
import kotlin.math.cos
import kotlin.math.sin


class TemperatureProgressBar(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {
    lateinit var binding: LayoutTemperatureProgressBarBinding
    var blurringPadding: Float = 0f
    var progressBarStrokeWidth: Float = 0f
    var outerCircleRadius: Float = 0f
    var innerCircleRadius: Float = 0f
    var sweepAngle: Float = 0f
    var degreeText = "0\u00B0"
    var outerCircleColor = R.color.pomegranate

    init {
        setWillNotDraw(false)
        binding =
            LayoutTemperatureProgressBarBinding.inflate(LayoutInflater.from(context), this, true)

        attrs?.let {
            val ta = context.obtainStyledAttributes(
                it, R.styleable.TemperatureProgressBar, 0, 0
            )

            blurringPadding =
                ta.getDimension(R.styleable.TemperatureProgressBar_blurring_padding, 20f)
            progressBarStrokeWidth =
                ta.getDimension(R.styleable.TemperatureProgressBar_progress_bar_stroke_width, 0f)
            outerCircleRadius =
                ta.getDimension(R.styleable.TemperatureProgressBar_outer_circle_radius, 0f)
            innerCircleRadius =
                ta.getDimension(R.styleable.TemperatureProgressBar_inner_circle_radius, 0f)
        }

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawMainCircle(canvas)
        drawProgressArc(canvas)
        drawProgressIndicator(canvas)
    }

    private fun drawMainCircle(canvas: Canvas?) {
        val colors = intArrayOf(
            ContextCompat.getColor(context, R.color.yellow),
            ContextCompat.getColor(context, R.color.wild_strawberry),
            ContextCompat.getColor(context, R.color.pink_flamingo),
            ContextCompat.getColor(context, R.color.pink_flamingo),
            ContextCompat.getColor(context, R.color.turbo),
            ContextCompat.getColor(context, R.color.yellow),
        )

        val sweepGradient = SweepGradient(
            width / 2f,
            height / 2f,
            colors,
            null
        )

        val rectF = RectF(
            0f + paddingLeft,
            0f + paddingTop,
            width.toFloat() - paddingRight,
            height.toFloat() - paddingBottom
        )

        val rectFBlur = RectF(
            0f - blurringPadding,
            0f - blurringPadding,
            width.toFloat() + blurringPadding,
            height.toFloat() + blurringPadding
        )

        val paint = Paint()
        paint.strokeWidth = progressBarStrokeWidth
        paint.shader = sweepGradient
        paint.style = Paint.Style.STROKE
        canvas?.drawArc(rectF, 0f, 360f, false, paint);

        canvas?.drawBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.ic_blur),
            null,
            rectFBlur,
            null
        )
    }

    private fun drawProgressArc(canvas: Canvas?) {
        val rectF = RectF(
            0f + paddingLeft,
            0f + paddingTop,
            width.toFloat() - paddingRight,
            height.toFloat() - paddingBottom
        )

        val progressPaint = Paint()
        progressPaint.color = Color.WHITE
        progressPaint.style = Paint.Style.STROKE
        progressPaint.strokeCap = Paint.Cap.ROUND
        progressPaint.strokeWidth = 5f
        canvas?.drawArc(rectF, 90f, sweepAngle, false, progressPaint);
    }

    private fun drawProgressIndicator(canvas: Canvas?) {
        val endX: Double =
            cos(Math.toRadians((sweepAngle + 90).toDouble())) * (width / 2 - paddingLeft) + width / 2

        val endY: Double =
            sin(Math.toRadians((sweepAngle + 90).toDouble())) * (width / 2 - paddingLeft) + height / 2

        val outerCirclePaint = Paint()
        outerCirclePaint.color = ContextCompat.getColor(context, outerCircleColor)
        outerCirclePaint.style = Paint.Style.FILL

        canvas?.drawCircle(endX.toFloat(), endY.toFloat(), outerCircleRadius, outerCirclePaint)

        val innerCirclePaint = Paint()
        innerCirclePaint.color = Color.WHITE
        innerCirclePaint.style = Paint.Style.FILL

        canvas?.drawCircle(endX.toFloat(), endY.toFloat(), innerCircleRadius, innerCirclePaint)
    }

    fun animateProgress(percentage: Float, degree: Int) {
        val angle = percentage * 360
        val progressAnimator = ValueAnimator.ofFloat(0f, angle)

        progressAnimator.duration = 800
        progressAnimator.startDelay = 500
        progressAnimator.interpolator = AccelerateDecelerateInterpolator()
        progressAnimator.addUpdateListener {
            sweepAngle = it.animatedValue as Float
            outerCircleColor = getOuterCircleColor(sweepAngle)
        }

        val degreeAnimator = ValueAnimator.ofInt(0, degree)

        degreeAnimator.duration = 800
        degreeAnimator.startDelay = 500
        degreeAnimator.interpolator = AccelerateDecelerateInterpolator()
        degreeAnimator.addUpdateListener {
            degreeText = (it.animatedValue as Int).toString() + "°"
            binding.tvDegree.text = degreeText
        }

        progressAnimator.start()
        degreeAnimator.start()
    }

    private fun getOuterCircleColor(angle: Float): Int {
        return when (angle % 360f) {
            in 0.0..25.0 -> R.color.pomegranate
            in 25.0..150.0 -> R.color.brilliant_rose
            in 150.0..190.0 -> R.color.pizazz
            in 340.0..360.0 -> R.color.pomegranate
            else -> R.color.koromiko
        }
    }
}