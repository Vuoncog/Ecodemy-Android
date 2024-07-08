package com.kltn.ecodemy.domain.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.constant.formatToMessageDateString
import com.kltn.ecodemy.constant.toLocalDateTime
import com.kltn.ecodemy.domain.models.NotificationMapper
import com.kltn.ecodemy.domain.repository.RealmDataProcess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val realmDataProcess: RealmDataProcess
) : ViewModel() {

    private val _notificationList =
        MutableStateFlow(emptyMap<LocalDate, List<NotificationMapper>>())
    val notificationList = _notificationList.asStateFlow()

    init {
        getAllData()
    }

    private fun getAllData() {
        viewModelScope.launch {
            realmDataProcess.getAllData().collect { list ->
                _notificationList.value = list.groupBy { notification ->
                    notification.time.toLocalDateTime().toLocalDate()
                }
                    .toSortedMap(
                        compareByDescending { it }
                    )
            }
        }

    }
}