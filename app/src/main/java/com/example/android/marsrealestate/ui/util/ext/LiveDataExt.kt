package com.example.android.marsrealestate.ui.util.ext

import androidx.lifecycle.MutableLiveData
import com.example.android.marsrealestate.ui.util.event.Event

fun <T> MutableLiveData<Event<T>>.postEvent(content: T) {
    postValue(Event(content))
}