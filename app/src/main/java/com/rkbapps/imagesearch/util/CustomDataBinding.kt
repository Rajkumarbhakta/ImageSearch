package com.rkbapps.imagesearch.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageFromInternet")
fun ImageView.loadFromInternet(url:String){
    Glide.with(this.context).load(url).into(this)
}



