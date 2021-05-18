package dev.mrbe.hymnary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dev.mrbe.hymnary.databinding.FragmentMyHymnsBinding


class MyHymnsFragment : Fragment() {
    //binding var
    private lateinit var binding: FragmentMyHymnsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyHymnsBinding.inflate(inflater, container, false)

        binding.myHymnsFab.setOnClickListener {
            findNavController().navigate(
                    MyHymnsFragmentDirections.actionMyHymnsFragmentToHymnsFormFragment()
            )
        }

        return binding.root
    }


}