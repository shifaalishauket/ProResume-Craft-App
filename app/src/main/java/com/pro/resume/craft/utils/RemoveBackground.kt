package com.pro.resume.craft.utils

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.slowmac.autobackgroundremover.BackgroundRemover
import com.slowmac.autobackgroundremover.OnBackgroundChangeListener

class RemoveBackground {
    companion object{
        fun removeBG(
            image: Bitmap,
            photo: ImageView,
            progress: ProgressBar
        ){
            BackgroundRemover.bitmapForProcessing(
                image,
                false,
                object: OnBackgroundChangeListener {
                    override fun onSuccess(bitmap: Bitmap) {
                        photo.setImageBitmap(bitmap)
                        progress.visibility = View.GONE
                    }

                    override fun onFailed(exception: Exception) {
                        photo.setImageBitmap(image)
                        progress.visibility = View.GONE
                    }
                }
            )

        }
    }
}