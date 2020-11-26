package com.example.android.marsrealestate.ui.overview

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.data.MarsProperty
import com.example.android.marsrealestate.data.remote.MarsApiStatus

@BindingAdapter("propertyList")
fun bindPropertyList(listView: RecyclerView, properties: List<MarsProperty>?) =
        (listView.adapter as MarsPropertyAdapter).submitList(properties ?: emptyList())

@BindingAdapter("apiCallStatus")
fun bindApiCallStatus(view: View, apiCallStatus: MarsApiStatus) {
    when (apiCallStatus) {
        MarsApiStatus.LOADING -> when (view) {
            is ImageView -> view.visibility = View.GONE
            is ProgressBar -> {
                view.visibility = View.VISIBLE
            }
        }

        MarsApiStatus.SUCCESS -> view.visibility = View.GONE
        MarsApiStatus.ERROR -> when (view) {
            is ImageView -> view.visibility = View.VISIBLE
            is ProgressBar -> {
                view.visibility = View.GONE
            }
        }
    }
}