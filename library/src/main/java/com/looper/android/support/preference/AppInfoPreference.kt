package com.looper.android.support.preference

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder

import com.looper.android.support.util.AppUtils
import com.looper.android.support.R

class AppInfoPreference(context: Context, attrs: AttributeSet) : Preference(context, attrs) {

    init {
        layoutResource = R.layout.preference_app_info
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        
        // Disable click on the entire preference item.
        holder.itemView.isClickable = false

        // Set the app version to the TextView.
        val appVersionTextView: TextView = holder.itemView.findViewById(R.id.app_version)        
        appVersionTextView.text = "v${AppUtils.getVersion(getContext())}"
    }
}