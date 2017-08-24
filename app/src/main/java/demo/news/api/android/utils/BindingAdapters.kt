package com.cafrecode.obviator.util

import android.databinding.BindingAdapter
import android.graphics.Typeface
import android.text.format.DateUtils
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

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

        @JvmStatic @BindingAdapter("app:loadUr")
        fun loadUrl(webView: WebView, url: String) {
            webView.loadUrl(url)
        }

        @JvmStatic @BindingAdapter("visibleGone")
        fun showHide(view: View, show: Boolean) {
            view.setVisibility(if (show) View.VISIBLE else View.GONE)
        }

        @JvmStatic @BindingAdapter("app:time")
        fun formatTime(textView: TextView, time: String) {
            //"2017-08-24T16:01:00Z"
            var pattern = "yyyy-MM-dd'T'HH:mm:ssZ"

            try {
                val sdf: SimpleDateFormat = SimpleDateFormat(pattern)
                val parsed = sdf.parse(time)
                textView.text = DateUtils.getRelativeTimeSpanString(textView.context, parsed.time)
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }
}