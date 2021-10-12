package dev.mrbe.hymnary

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dev.mrbe.hymnary.MediaDialogFragment.Companion.TAG
import dev.mrbe.hymnary.databinding.UserProfileBinding
import timber.log.Timber

class ProfileFragment : Fragment() {
    //firebase bucket var
    private val storage: FirebaseStorage = Firebase.storage

    //storage ref
    var storageRef = storage.reference

    //userDp ref
    var dpRef: StorageReference? = storageRef.child("userDisplayPictures")


    //binding
    private lateinit var binding: UserProfileBinding

    var STATE_URI = "uri"
    var mUri: Uri? = null

    /* Use shared viewmodel*/
    val viewModel: UserActivityViewModel by activityViewModels()

    companion object {
        fun newInstance() = ProfileFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserProfileBinding.inflate(inflater, container, false)

        //set up media selector button
        binding.profieSelectMediaButton.setOnClickListener {

            MediaDialogFragment().show(
                childFragmentManager, TAG
            )
        }

      return binding.root
    }

    override fun onResume() {
        super.onResume()

        Timber.d("Tag: onResume, before User Dp Pref restore")
        // restore pref
        if (!restorePrefData().isNullOrEmpty()) {
            downloadDp(Uri.parse(restorePrefData()))
            Timber.d("Tag: User pref data is ${restorePrefData()}")
        }
        viewModel.downloadUrl.observe(viewLifecycleOwner, { downloadUri ->
            downloadDp(downloadUri)
            binding.textDemo.text = downloadUri.toString()

            //set pref
            savePrefData(downloadUri.toString())
        })
    }

    // Get image with Glide
    private fun downloadDp(uri: Uri?) {
        val imageView = binding.circularImageView
        Glide.with(this)
            .load(uri)
            .placeholder(R.drawable.ic_baseline_person_24)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)
    }

    /*Pref files*/
    private fun savePrefData(userPrefDpLink: String) {
        //shared pref
        val userDpPref =
            activity?.getSharedPreferences(getString(R.string.user_pref_name), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor? = userDpPref?.edit()
        editor?.putString(getString(R.string.user_pref_string_key), userPrefDpLink)
        editor?.putBoolean(getString(R.string.user_pref_key), true)
        editor?.apply()
    }

    private fun restorePrefData(): String? {
        val pref: SharedPreferences? =
            activity?.getSharedPreferences(getString(R.string.user_pref_name), Context.MODE_PRIVATE)
        return if (pref?.getBoolean(getString(R.string.user_pref_key), false) == true) {
            pref.getString(
                (getString(R.string.user_pref_string_key)),
                getString(R.string.saved_default_dp_link)
            )
        } else null
    }
}