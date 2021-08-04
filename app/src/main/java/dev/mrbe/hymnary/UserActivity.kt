package dev.mrbe.hymnary

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.mrbe.hymnary.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {


//    //firebase bucket var
//    val storage: FirebaseStorage = Firebase.storage
//
//    //storage ref
//    var storageRef = storage.reference
//
//    //userDp ref
//    var userDpRef:StorageReference? = storageRef.child("userDisplayPictures")

    //binding var
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    companion object {
        private const val TAG = "Hymnary"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}