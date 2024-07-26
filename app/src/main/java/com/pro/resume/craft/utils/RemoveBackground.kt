package com.pro.resume.craft.utils

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.slowmac.autobackgroundremover.BackgroundRemover
import com.slowmac.autobackgroundremover.OnBackgroundChangeListener

class RemoveBackground {
    companion object {
        interface BackgroundRemovalCallback {
            fun onSuccess(bitmap: Bitmap)
            fun onFailure(exception: Exception)
        }

        fun removeBG(
            image: Bitmap,
            photo: ImageView,
            progress: ProgressBar,
            callback: BackgroundRemovalCallback
        ) {
            BackgroundRemover.bitmapForProcessing(
                image,
                false,
                object : OnBackgroundChangeListener {
                    override fun onSuccess(bitmap: Bitmap) {
                        photo.setImageBitmap(bitmap)
                        progress.visibility = View.GONE
                        callback.onSuccess(bitmap)
                    }

                    override fun onFailed(exception: Exception) {
                        photo.setImageBitmap(image)
                        progress.visibility = View.GONE
                        callback.onFailure(exception)
                    }
                }
            )
        }
    }
}
