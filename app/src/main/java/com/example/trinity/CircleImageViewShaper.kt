package com.example.trinity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CircleImageViewShaper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = drawable ?: return

        if (width == 0 || height == 0) {
            return
        }

        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        val widthRatio = width.toFloat() / bitmap.width
        val heightRatio = height.toFloat() / bitmap.height
        val scale = Math.max(widthRatio, heightRatio)

        val scaledWidth = scale * bitmap.width
        val scaledHeight = scale * bitmap.height

        val left = (width - scaledWidth) / 2
        val top = (height - scaledHeight) / 2

        paint.shader = shader
        canvas.drawCircle(
            width / 2f,
            height / 2f,
            Math.min(width, height) / 2f,
            paint
        )
    }
}
