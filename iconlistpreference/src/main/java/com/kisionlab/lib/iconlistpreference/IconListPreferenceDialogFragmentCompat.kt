package com.kisionlab.lib.iconlistpreference

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.preference.ListPreferenceDialogFragmentCompat
import androidx.core.content.res.TypedArrayUtils.getResourceId
import android.content.res.TypedArray
import androidx.core.view.ViewCompat
import com.kisionlab.lib.iconlistpreference.utils.ViewUtils


class IconListPreferenceDialogFragmentCompat : ListPreferenceDialogFragmentCompat() {
    protected lateinit var iconListPreference: IconListPreference

    protected var entries: Array<CharSequence>? = null
    protected var entryValues: Array<CharSequence>? = null

    override fun onPrepareDialogBuilder(builder: AlertDialog.Builder) {
        entries = iconListPreference.entries
        entryValues = iconListPreference.entryValues

        if (entries == null || entryValues == null || entries!!.size != entryValues!!.size) {
            throw IllegalStateException("ListPreference requires an entries array and an entryValues array which are both the same length")
        }

        val iconListPreferenceAdapter = IconListPreferenceScreenAdapter(context!!)
        builder.setAdapter(iconListPreferenceAdapter, null)
        builder.setPositiveButton(null, null)
    }

    private inner class IconListPreferenceScreenAdapter(context: Context) : BaseAdapter() {

        override fun getCount(): Int {
            return entries?.size ?: 0
        }

        internal inner class CustomHolder(row: View, position: Int) {
            private var title: TextView? = null
            private var summary: TextView? = null
            private var image: ImageView? = null
            private var rButton: RadioButton? = null

            init {
                title = row.findViewById(R.id.image_list_view_row_title_text_view) as TextView
                title!!.text = entries?.get(position) ?: ""

                rButton = row.findViewById(R.id.image_list_view_row_radio_button) as RadioButton
                rButton!!.id = position
                rButton!!.isClickable = false
                rButton!!.isChecked = iconListPreference.selectedItemIndex == position

                image = row.findViewById(R.id.image_list_view_row_image_view) as ImageView
                image!!.visibility = View.GONE

                summary = row.findViewById(R.id.image_list_view_row_summary_text_view) as TextView
                summary!!.visibility = View.GONE

                iconListPreference.entryIcons?.get(position)?.let {
                    image!!.setImageResource(it)
                    image!!.visibility = View.VISIBLE
                }
                iconListPreference.entryDrawables?.get(position)?.let {
                    image!!.setImageDrawable(it)
                    image!!.visibility = View.VISIBLE
                }
                iconListPreference.entrySummaries?.get(position)?.let {
                    summary!!.setText(it)
                    summary!!.visibility = View.VISIBLE
                }
            }
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var row = convertView
            var holder: CustomHolder? = null
            row = LayoutInflater.from(context!!).inflate(
                R.layout.image_list_preference_row,
                parent,
                false)
            holder = CustomHolder(row, position)
            row.tag = holder
            row.isClickable = true
            row.setBackgroundResource(
                ViewUtils.getBackgroundFromAttr(context!!, R.attr.selectableItemBackground)
            )

            row.setOnClickListener { v ->
                v.requestFocus()

                val mDialog = dialog
                mDialog!!.dismiss()

                entryValues?.get(position).toString().let {
                    iconListPreference.updatePref(it)
                }
            }

            return row
        }

    }

    companion object {

        fun newInstance(preference: IconListPreference): IconListPreferenceDialogFragmentCompat {
            val fragment = IconListPreferenceDialogFragmentCompat()
            val bundle = Bundle(1)
            bundle.putString("key", preference.getKey())
            fragment.arguments = bundle

            // Probably totally illegal, but whatever
            fragment.iconListPreference = preference

            return fragment
        }
    }
}