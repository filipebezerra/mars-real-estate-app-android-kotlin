package com.example.android.marsrealestate.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.MarsPropertyGridItemBinding
import com.example.android.marsrealestate.data.MarsProperty
import com.example.android.marsrealestate.ui.overview.MarsPropertyViewHolder.Companion.from

class MarsPropertyAdapter() : ListAdapter<MarsProperty, MarsPropertyViewHolder>(
        MarsPropertyDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = from(parent)

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) =
            holder.bindTo(getItem(position))
}

class MarsPropertyViewHolder private constructor(
        private val itemViewBinding: MarsPropertyGridItemBinding,
) : RecyclerView.ViewHolder(itemViewBinding.root) {

    init {
        itemViewBinding.setClickListener {
            itemViewBinding.property?.let { property ->
                navigateToDetail(property, it)
            }
        }
    }

    private fun navigateToDetail(property: MarsProperty, view: View) {
        view.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(property))
    }

    fun bindTo(item: MarsProperty) =
            with(itemViewBinding) {
                property = item
                executePendingBindings()
            }

    companion object {
        fun from(parent: ViewGroup) =
                MarsPropertyViewHolder(
                        MarsPropertyGridItemBinding.inflate(
                                LayoutInflater.from(parent.context),
                                parent,
                                false
                        ),
                )
    }
}

class MarsPropertyDiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
    override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty) =
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty) =
            oldItem == newItem
}
