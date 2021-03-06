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

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.data.remote.MarsPropertyFilter
import com.example.android.marsrealestate.databinding.OverviewFragmentBinding
import com.example.android.marsrealestate.ui.util.ext.SnackBarAction
import com.example.android.marsrealestate.ui.util.ext.setupSnackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by viewModels()

    private lateinit var viewBinding: OverviewFragmentBinding

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = OverviewFragmentBinding.inflate(inflater).apply {
        viewBinding = this
        lifecycleOwner = viewLifecycleOwner
        viewModel = this@OverviewFragment.viewModel

        setHasOptionsMenu(true)
        createMarsPropertyAdapter()
        subscribeUi()
    }.root

    private fun createMarsPropertyAdapter() {
        with(viewBinding.marsPropertyList) {
            adapter = MarsPropertyAdapter()
        }
    }

    private fun subscribeUi() {
        with(viewModel) {
            propertyFilter.observe(viewLifecycleOwner) {
                activity?.invalidateOptionsMenu()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setupSnackbar(
                viewLifecycleOwner,
                viewModel.snackBarText,
                LENGTH_INDEFINITE,
                action = SnackBarAction(R.string.retry_load_mars_properties) {
                    viewModel.loadMarsRealEstateProperties()
                }
        )
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        when (viewModel.propertyFilter.value) {
            MarsPropertyFilter.FOR_RENT -> R.id.show_for_rent_properties
            MarsPropertyFilter.FOR_BUY -> R.id.show_for_buy_properties
            else -> R.id.show_all_properties
        }.run { menu.findItem(this) }.apply { this.isChecked = true }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.show_all_properties,
        R.id.show_for_rent_properties,
        R.id.show_for_buy_properties -> {
            viewModel.updatePropertyFilter(item.itemId)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
