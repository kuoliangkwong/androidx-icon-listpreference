package com.kisionlab.lib.iconlistpreference.utils

import android.content.Context
import com.kisionlab.lib.iconlistpreference.R

class ViewUtils {

    companion object {
        fun getBackgroundFromAttr(context: Context, attribId: Int): Int {
            val attrs = intArrayOf(R.attr.selectableItemBackground)
            val typedArray = context.obtainStyledAttributes(attrs)
            val backgroundResId =  typedArray.getResourceId(0, 0)
            typedArray.recycle()
            return backgroundResId
        }
    }
}