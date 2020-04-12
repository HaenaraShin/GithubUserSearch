package dev.haenara.githubsearch.ui

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dev.haenara.githubsearch.api.LOG_TAG

fun View.showIf(condition : Boolean) {
    visibility = if (condition) { View.VISIBLE } else { View.GONE }
}

fun ImageView.setImageFromUrl(context: Context, url: String, toHide : View? = null): ImageView {
    val progressDoneListener = object : RequestListener<Bitmap?> {
        override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap?>?,
                isFirstResource: Boolean
        ): Boolean {
            toHide?.let {
                it.visibility = View.INVISIBLE
            }
            return false
        }

        override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
        ): Boolean {
            toHide?.let {
                it.visibility = View.INVISIBLE
            }
            return false
        }
    }

    Glide.with(context)
            .asBitmap()
            .load(url)
            .centerCrop()
            .circleCrop()
            .thumbnail(0.4f)
            .listener(progressDoneListener)
            .into(this)
    return this
}