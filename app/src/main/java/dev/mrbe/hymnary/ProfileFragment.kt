package dev.mrbe.hymnary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dev.mrbe.hymnary.MediaDialogFragment.Companion.TAG
import dev.mrbe.hymnary.databinding.UserProfileBinding

class ProfileFragment : Fragment() {
    //binding
    private lateinit var binding: UserProfileBinding

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
        viewModel.image.observe(viewLifecycleOwner, { image ->
            binding.circularImageView.setImageDrawable(image)
        })
    }
}