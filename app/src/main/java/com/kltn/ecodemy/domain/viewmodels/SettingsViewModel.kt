package com.kltn.ecodemy.domain.viewmodels

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.data.impl.PreferenceKey
import com.kltn.ecodemy.domain.models.language.LanguageTag
import com.kltn.ecodemy.domain.repository.DataStorePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStorePreferences: DataStorePreferences
) : ViewModel() {
    private val _languageTag = MutableStateFlow(LanguageTag.ENGLISH)
    val languageTag = _languageTag.asStateFlow()

    init {
        readLanguageFromDataStore()
    }

    private fun setLanguageTag(tag: LanguageTag){
        _languageTag.value = tag
    }

    fun changeLanguage(tag: LanguageTag, context: Context) {
        viewModelScope.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                dataStorePreferences.saveData(data = tag.tag, pref = PreferenceKey.languagePref)
                context.getSystemService(
                    LocaleManager::class.java
                ).applicationLocales = LocaleList(Locale.forLanguageTag(tag.tag))
            } else {
                dataStorePreferences.saveData(data = tag.tag, pref = PreferenceKey.languagePref)
                val config = context.resources.configuration
                val resources = context.resources
                val lang = tag.tag
                val locale = Locale(lang)
                Locale.setDefault(locale)
                config.setLocale(locale)

                context.createConfigurationContext(config)
                resources.updateConfiguration(config, resources.displayMetrics)
            }
            setLanguageTag(tag)
        }
    }

    private fun readLanguageFromDataStore() {
        viewModelScope.launch {
            val langTag = dataStorePreferences.readData(pref = PreferenceKey.languagePref)
                .stateIn(viewModelScope).value
            _languageTag.value = LanguageTag.of(langTag ?: "en") ?: LanguageTag.ENGLISH
        }
    }
}