package com.kisionlab.lib.iconlistpreference

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.preference.PreferenceManager
import android.util.AttributeSet
import androidx.preference.ListPreference
import com.kisionlab.lib.iconlistpreference.utils.TypedArrayUtils

open class IconListPreference: ListPreference {

    var entrySummaries: Array<CharSequence>? = null
    var entryIcons: IntArray? = null
    var entryDrawables: Array<Drawable>? = null
    private var prefs: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    val selectedItemIndex: Int
        get() = findIndexOfValue(value)

    constructor(context: Context?): this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, TypedArrayUtils.getAttr(
        context!!,
        androidx.preference.R.attr.dialogPreferenceStyle,
        android.R.attr.dialogPreferenceStyle
    ))
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : this(context, attrs, defStyle, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int, defStyleRes: Int): super(context, attrs, defStyle, defStyleRes) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        editor = prefs?.edit()
        val a = context!!.obtainStyledAttributes(attrs, R.styleable.IconPreference, defStyle, 0)
        val entryIconsResId = a.getResourceId(R.styleable.IconPreference_entryIcons, -1)
        if (entryIconsResId != -1) {
            setEntryIcons(entryIconsResId)
        }
        val entrySummariesResId = a.getResourceId(R.styleable.IconPreference_entrySummaries, -1)
        if (entrySummariesResId != -1) {
            setEntrySummaries(entrySummariesResId)
        }
        a.recycle()
    }

    fun setEntryIcons(entryIconsResId: Int) {
        val icons_array = context.resources.obtainTypedArray(entryIconsResId)
        val icon_ids_array = IntArray(icons_array.length())
        for (i in 0 until icons_array.length()) {
            icon_ids_array[i] = icons_array.getResourceId(i, -1)
        }
        entryIcons = icon_ids_array
        icons_array.recycle()
    }

    fun setEntrySummaries(entryIconsResId: Int) {
        val summaries_array = context.resources.obtainTypedArray(entryIconsResId)
        val summary_ids_list = mutableListOf<CharSequence>()
        for (i in 0 until summaries_array.length()) {
            summary_ids_list.add(summaries_array.getString(i))
        }
        entrySummaries = summary_ids_list.toTypedArray()
        summaries_array.recycle()
    }

    fun updatePref(value: String) {
        setValue(value)
        callChangeListener(value)
    }
}