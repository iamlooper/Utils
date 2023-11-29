package com.looper.android.support.preference

import android.view.LayoutInflater

import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

import com.looper.android.support.R

/**
 * PreferenceFragmentCompat using the [MaterialAlertDialogBuilder] instead of the old dialog builder.
 */
abstract class PreferenceFragment : PreferenceFragmentCompat() {
    /**
     * Whether any preference dialog is currently visible to the user.
     */
    var isDialogVisible = false
    
    override fun onDisplayPreferenceDialog(preference: Preference) {
        // Can be set to true here since we only use the following preferences with dialogs.
        isDialogVisible = true

        // Handle different types of preferences and show custom dialogs.
        when (preference) {
            /**
             * Show a [MaterialAlertDialogBuilder] when the preference is a [ListPreference]
             */
            is ListPreference -> {
                // Get the index of the previous selected item.
                val prefIndex = preference.entryValues.indexOf(preference.value)
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(preference.title)
                    .setSingleChoiceItems(preference.entries, prefIndex) { dialog, index ->
                        // Get the new ListPreference value.
                        val newValue = preference.entryValues[index].toString()
                        // Invoke the on change listeners.
                        if (preference.callChangeListener(newValue)) {
                            preference.value = newValue
                        }
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.cancel, null)
                    .setOnDismissListener { isDialogVisible = false }
                    .show()
            }

            is MultiSelectListPreference -> {
                // Prepare an array indicating selected items.
                val selectedItems = preference.entryValues.map {
                    preference.values.contains(it)
                }.toBooleanArray()
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(preference.title)
                    .setMultiChoiceItems(preference.entries, selectedItems) { _, _, _ ->
                        // Get the new selected values from MultiSelectListPreference.
                        val newValues = preference.entryValues
                            .filterIndexed { index, _ -> selectedItems[index] }
                            .map { it.toString() }
                            .toMutableSet()
                        if (preference.callChangeListener(newValues)) {
                            preference.values = newValues
                        }
                    }
                    .setPositiveButton(R.string.okay, null)
                    .setOnDismissListener { isDialogVisible = false }
                    .show()
            }

            is EditTextPreference -> {
                // Inflate the layout for EditTextPreference dialog.
                val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.preference_dialog_edit_text, null)
                val input: TextInputEditText = dialogView.findViewById(R.id.input)
                
                // Set the initial text value to EditTextPreference value.
                input.setText(preference.text)

                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(preference.title)
                    .setView(dialogView)
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        // Get the new value from EditTextPreference.
                        val newValue = input.text.toString()
                        if (preference.callChangeListener(newValue)) {
                            preference.text = newValue
                        }
                    }
                    .setNegativeButton(R.string.cancel, null)
                    .setOnDismissListener { isDialogVisible = false }
                    .show()
            }
            // Otherwise show the normal dialog.
            else -> super.onDisplayPreferenceDialog(preference)
        }
    }
}