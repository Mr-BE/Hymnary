package dev.mrbe.hymnary

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import dev.mrbe.hymnary.databinding.FragmentMediaDialogBinding
import timber.log.Timber
import java.io.File


class MediaDialogFragment : DialogFragment() {

    lateinit var binding: FragmentMediaDialogBinding

    /* Use shared viewmodel*/
    val viewModel: UserActivityViewModel by activityViewModels()

    //storage Directory
    val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    //content resolver
    lateinit var contentResolver: ContentResolver

    var imageUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMediaDialogBinding.inflate(inflater, container, false)

        //attach content resolver to fragment context
        contentResolver = requireContext().contentResolver

        //set up accept
        binding.accept.setOnClickListener {
            val selectedRadioButton = binding.radioG.checkedRadioButtonId
            if (selectedRadioButton == binding.cameraRB.id) {
                takeImage()
            } else {
                getExistingMedia.launch("image/*")
            }
        }
        binding.cancel.setOnClickListener { dismiss() }
        return binding.root
    }

    //pick image from device file
    private val getExistingMedia =
        registerForActivityResult(ActivityResultContracts.GetContent()) { mediaUri: Uri? ->
            imageUri = mediaUri
        }

    //take image with device camera app
    private fun takeImage() {
        val photoFile = viewModel.createImageFile(storageDir)
        photoFile.also {
            val photUri: Uri = FileProvider.getUriForFile(
                requireContext(),
                "${BuildConfig.APPLICATION_ID}.provider",
                it
            )
            imageUri = photUri
            cameraImageResult.launch(photUri)
        }
    }


    companion object {
        const val TAG = "DialogFrag"
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onResume() {
        super.onResume()
        if (imageUri == null) {
            Timber.d("Null image URI received")
        } else {
            viewModel.getBitmapFromUri(imageUri!!, contentResolver)?.let { viewModel.setImage(it) }
            dismiss()

        }
    }

    //launch device camera and return true if action was successful
    val cameraImageResult = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {
        if (it) {
            Timber.d("Image gotten and saved from camera")
        }
    }
}