package com.kisionlab.lib.iconlistpreference.utils

import android.content.Context
import android.util.TypedValue

class TypedArrayUtils {

    companion object {
        fun getAttr(context: Context, attr: Int, fallbackAttr: Int): Int {
            val value = TypedValue()
            context.theme.resolveAttribute(attr, value, true)
            return if (value.resourceId != 0) {
                attr
            } else fallbackAttr
        }
    }
}