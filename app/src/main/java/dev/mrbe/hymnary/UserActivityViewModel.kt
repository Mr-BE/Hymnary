package dev.mrbe.hymnary

import android.content.ContentResolver
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UserActivityViewModel : ViewModel() {
    private val _image = MutableLiveData<Drawable>()
    val image: LiveData<Drawable>
        get() = _image

    fun setImage(bitmap: Bitmap) {
        _image.value = BitmapDrawable(Resources.getSystem(), bitmap)
    }


    @Throws(IOException::class)
    fun createImageFile(storageDir: File?): File {
        lateinit var currentPhotoPath: String
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            var currentPhotoPath = absolutePath
        }
    }

    fun getBitmapFromUri(imageUri: Uri, contentResolver: ContentResolver): Bitmap? {
        var bitmap: Bitmap? = null

        try {
            bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            } else {
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

}