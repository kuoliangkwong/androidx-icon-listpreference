package com.kisionlab.lib.iconlistpreference

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceViewHolder

open class ClickableListPreference: IconListPreference {

    var isEnabledView = true
        protected set

    constructor(context: Context?): super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int, defStyleRes: Int): super(context, attrs, defStyle, defStyleRes)

    private fun enableView(view: View, enabled: Boolean, ignoreThisView: Boolean = false) {
        if (!ignoreThisView) {
            view.isEnabled = enabled
        }
        if (view is ViewGroup) {
            for (index in 0 until view.childCount)
                enableView(view.getChildAt(index), enabled)
        }
    }

    fun setEnabledViews(enabled: Boolean) {
        isEnabledView = enabled
        notifyChanged()
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)
        val viewEnabled = isEnabled && isEnabledView
        holder?.itemView?.let {
            enableView(it, viewEnabled, true)
        }
    }

    override fun onClick() {
        // Only allow onClick aka showing list dialog when this
        // isEnabledView is true
        if (isEnabledView) {
            super.onClick() //This will show the list dialog
        }
    }

}
