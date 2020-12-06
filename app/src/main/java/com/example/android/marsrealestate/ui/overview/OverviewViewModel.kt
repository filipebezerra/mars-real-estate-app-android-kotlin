/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.data.MarsProperty
import com.example.android.marsrealestate.data.remote.MarsApi
import com.example.android.marsrealestate.data.remote.MarsApiStatus
import com.example.android.marsrealestate.data.remote.MarsPropertyFilter
import com.example.android.marsrealestate.ui.util.event.Event
import com.example.android.marsrealestate.ui.util.ext.postEvent
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _properties = MutableLiveData<List<MarsProperty>>()

    // The external immutable LiveData for the request status String
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    private val _snackBarText = MutableLiveData<Event<Int>>()
    val snackBarText: LiveData<Event<Int>>
        get() = _snackBarText

    private val _apiCallStatus = MutableLiveData<MarsApiStatus>()
    val apiCallStatus: LiveData<MarsApiStatus>
        get() = _apiCallStatus

    private val _propertyFilter = MutableLiveData<MarsPropertyFilter>().apply {
        value = MarsPropertyFilter.ALL
    }
    val propertyFilter: LiveData<MarsPropertyFilter>
        get() = _propertyFilter

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        loadMarsRealEstateProperties()
    }

    fun loadMarsRealEstateProperties() = getMarsRealEstateProperties()

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties() {
        viewModelScope.launch {
            try {
                _apiCallStatus.value = MarsApiStatus.LOADING
                _properties.value = MarsApi.retrofitService.getProperties(
                        _propertyFilter.value?.type ?: MarsPropertyFilter.ALL.type
                )
                _apiCallStatus.value = MarsApiStatus.SUCCESS
            } catch (t: Exception) {
                _apiCallStatus.value = MarsApiStatus.ERROR
                _properties.value = emptyList()
                _snackBarText.postEvent(R.string.fail_to_get_properties_error_message)
            }
        }
    }

    fun updatePropertyFilter(optionId: Int) {
        _propertyFilter.value = when (optionId) {
            R.id.show_for_rent_properties -> MarsPropertyFilter.FOR_RENT
            R.id.show_for_buy_properties -> MarsPropertyFilter.FOR_BUY
            else -> MarsPropertyFilter.ALL
        }
        getMarsRealEstateProperties()
    }
}
