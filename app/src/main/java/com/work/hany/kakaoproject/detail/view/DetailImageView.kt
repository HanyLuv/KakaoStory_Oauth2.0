package com.work.hany.kakaoproject.detail.view

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by hany on 2018. 3. 11..
 */

class DetailImageView : AppCompatImageView {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val drawable = drawable
        if (drawable != null) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = Math.ceil((width.toFloat() * drawable.intrinsicHeight.toFloat() / drawable.intrinsicWidth.toFloat()).toDouble()).toInt()
            setMeasuredDimension(width, height)

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }

    }

}