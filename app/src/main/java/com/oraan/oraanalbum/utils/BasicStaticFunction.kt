package com.oraan.oraanalbum.utils

import android.graphics.PorterDuff
import android.view.MotionEvent
import android.view.View
import com.google.gson.Gson

object BasicStaticFunction {
    val FOCUS_TOUCH_LISTENER: View.OnTouchListener = View.OnTouchListener { v, event ->
        val drawable = v.background
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_BUTTON_PRESS -> {

                drawable.setColorFilter(0x20000000, PorterDuff.Mode.SRC_ATOP)
                v.invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                drawable.clearColorFilter()
                v.invalidate()
            }
        }
        false
    }

    fun convertToJson(data: Any) = Gson().toJson(data)

}