package com.test.kkl.testandroid1

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.preference.SwitchPreference
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.kisionlab.lib.iconlistpreference.ClickableListPreference
import com.kisionlab.lib.iconlistpreference.IconListPreference
import com.kisionlab.lib.iconlistpreference.IconListPreferenceDialogFragmentCompat

class SettingsFragment: PreferenceFragmentCompat() {

    private var mDefaultPreferences: SharedPreferences? = null

    private val mOnSharedPreferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val testIconPref = findPreference("test_icon_type")
        testIconPref.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            Log.v("kkl2", "testIconPref clicked")
            true
        }

        val testEnabledPref = findPreference("test_enabled_type") as ClickableListPreference
        testEnabledPref.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            Log.v("kkl2", "testEnabledPref clicked")
            true
        }
        testEnabledPref.setEnabledViews(true)

        val testDisabledPref = findPreference("test_disabled_type") as ClickableListPreference
        testDisabledPref.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            Log.v("kkl2", "testDisabledPref clicked")
            true
        }
        testDisabledPref.setEnabledViews(false)

    }

    override fun onResume() {
        super.onResume()

        mDefaultPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        mDefaultPreferences!!.registerOnSharedPreferenceChangeListener(mOnSharedPreferenceChangeListener)
    }

    override fun onPause() {
        super.onPause()
        mDefaultPreferences!!.unregisterOnSharedPreferenceChangeListener(mOnSharedPreferenceChangeListener)

    }

    override fun onDisplayPreferenceDialog(preference: Preference?) {
        if (preference is IconListPreference) {
            val iconListDialog = IconListPreferenceDialogFragmentCompat.newInstance(preference)
            iconListDialog.setTargetFragment(this, 0)
            iconListDialog.show(fragmentManager!!, "android.support.v7.preference.PreferenceFragment.DIALOG")
        } else {
            super.onDisplayPreferenceDialog(preference)
        }
    }
}