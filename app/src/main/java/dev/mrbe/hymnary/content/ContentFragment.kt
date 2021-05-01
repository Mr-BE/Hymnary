package dev.mrbe.hymnary.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dev.mrbe.hymnary.Hymn

import dev.mrbe.hymnary.databinding.FragmentContentBinding
import timber.log.Timber


class ContentFragment : Fragment() {
    private lateinit var binding: FragmentContentBinding
    private lateinit var viewModel: ContentViewModel
    private lateinit var hymn: Hymn


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hymn = ContentFragmentArgs.fromBundle(requireArguments()).argHymn
        viewModel = ViewModelProvider(this, ContentViewModel.ContentViewModelFactory(hymn))
            .get(ContentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //set up binding variables
        binding = FragmentContentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }


}