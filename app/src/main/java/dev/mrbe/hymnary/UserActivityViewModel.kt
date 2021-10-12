package dev.mrbe.hymnary

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UserActivityViewModel : ViewModel() {

    //Firebase user
    val user = Firebase.auth.currentUser

    //firebase bucket var
    val storage: FirebaseStorage = Firebase.storage

    //storage ref
    var storageRef = storage.reference

    //userDp ref
    var userDpRef: StorageReference? = storageRef.child("userDisplayPictures")
        .child("${user?.uid}").child("dp")

    private val _downloadUrl = MutableLiveData<Uri?>()
    val downloadUrl: LiveData<Uri?>
        get() = _downloadUrl

    private val _networkProgress = MutableLiveData<Long>()
    val networkProgress: LiveData<Long>
        get() = _networkProgress


    private val _image = MutableLiveData<Drawable>()
    val image: LiveData<Drawable>
        get() = _image

    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri>
        get() = _imageUri

    private fun setImage(bitmap: Bitmap) {
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
        //assign created uri to Live data
        _imageUri.value = imageUri
        return bitmap
    }

    fun setImageUri(uri: Uri?) {
        _imageUri.value = uri!!

    }


    fun firebaseUploadAndDownloadTask(
        uri: Uri,
        context: Context,
        contentResolver: ContentResolver
    ) {

        val uploadTask = userDpRef?.putFile(uri)

            ?.addOnProgressListener { uploadTask ->
                val uploadProgress = uploadTask.bytesTransferred / uploadTask.totalByteCount
                _networkProgress.postValue(uploadProgress)
                Log.i("TAG", "Progress is $uploadProgress")

            }
            ?.addOnSuccessListener {
                Toast.makeText(context, "Upload Complete", Toast.LENGTH_LONG).show()
            }

            ?.addOnCompleteListener {
                //download
                userDpRef?.downloadUrl?.addOnCompleteListener {
                    _downloadUrl.value = it.result
                    Timber.d("Url is ${it.result}.toString()")


                }
            }

    }


}