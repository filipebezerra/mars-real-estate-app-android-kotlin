package com.example.android.marsrealestate.ui.detail

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.data.MarsProperty

@BindingAdapter("propertyType")
fun bindPropertyType(textView: TextView, marsProperty: MarsProperty) =
        with(textView) {
            text = context.getString(R.string.display_type,
                    if (marsProperty.isRental) context.getString(R.string.type_rent) else
                        context.getString(R.string.type_sale))
        }

@BindingAdapter("propertyPrice")
fun bindPropertyPrice(textView: TextView, marsProperty: MarsProperty) =
        with(textView) {
            text = if (marsProperty.isRental)
                context.getString(R.string.display_price_monthly_rental, marsProperty.price) else
                    context.getString(R.string.display_price, marsProperty.price)
        }