package com.abnamro.abnhub.ui.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * data binding adapter for setting ImageView's picture in xml
 */
@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url: String?) {
    if (url != null && url.isNotBlank()) {
        Glide.with(this).load(url).circleCrop().into(this)
    }
}
