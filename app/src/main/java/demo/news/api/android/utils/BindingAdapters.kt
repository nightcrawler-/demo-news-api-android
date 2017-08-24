package com.cafrecode.obviator.util

import android.databinding.BindingAdapter
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

/**
 * Created by Frederick on 20/08/2017.
 */
class BindingAdapters {

    companion object {

        @JvmStatic @BindingAdapter("app:font_file")
        fun setFont(textView: TextView, fontFile: String) {
            textView.typeface = Typeface.createFromAsset(textView.context.assets, fontFile)
        }

        @JvmStatic @BindingAdapter("app:src")
        fun setImage(imageView: ImageView, uri: String?) {
            Picasso.with(imageView.context).load(uri).into(imageView)
        }

    }
}