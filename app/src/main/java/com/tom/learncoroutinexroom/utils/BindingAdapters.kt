package com.tom.learncoroutinexroom.utils

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("image")
fun loadImage(img: AppCompatImageView, imagePath: String?) {
    Glide.with(img.context)
        .asBitmap()
        .transform(BlurTransformation(img.context))
        .load(imagePath)
        .circleCrop()
        .into(img)
}