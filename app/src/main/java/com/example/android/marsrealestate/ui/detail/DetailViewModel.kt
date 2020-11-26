/*
 *  Copyright 2018, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.android.marsrealestate.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.marsrealestate.data.MarsProperty

/**
 * The [ViewModel] that is associated with the [DetailFragment].
 */
class DetailViewModel(
    private val marsProperty: MarsProperty,
) : ViewModel() {

    private val _property = MutableLiveData<MarsProperty>().apply {
        value = marsProperty
    }
    val property: LiveData<MarsProperty>
        get() = _property

    companion object {

        fun provideFactory(
            marsProperty: MarsProperty,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("unchecked_cast")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                DetailViewModel(marsProperty) as T
        }
    }
}
