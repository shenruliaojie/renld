package com.ligw.renld.view

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.ligw.renld.R


/**
 * @author created by ligw on 2022/8/15
 * @Email ligw@wanbu.com.cn
 */
class DeleteEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {
    var iconDrawable:Drawable ?= null

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ClearEditText,
            0,0
        ).apply {
            try {
                var resourceId = getResourceId(R.styleable.ClearEditText_clearIcon,0)
                if(resourceId!=0){
                    iconDrawable = ContextCompat.getDrawable(context,resourceId)
                }
            } finally {
                recycle()
            }
        }
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        toggleClearIcon()
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        toggleClearIcon()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { e->
            iconDrawable?.let {
                if(e.x > width-it.intrinsicWidth && e.x < width
                    && e.y > height/2 - it.intrinsicHeight
                    && e.y < height/2 + it.intrinsicHeight){
                    text?.clear()
                }
            }
        }
        return super.onTouchEvent(event)
    }


    fun toggleClearIcon(){
        var icon = if (isFocused && text?.isNotEmpty() == true) iconDrawable else null
        setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,icon,null)
    }


}