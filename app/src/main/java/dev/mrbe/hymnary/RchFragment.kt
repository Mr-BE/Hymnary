package dev.mrbe.hymnary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dev.mrbe.hymnary.databinding.FragmentRchBinding

import timber.log.Timber


class RchFragment : Fragment() {

    private lateinit var binding: FragmentRchBinding
    private lateinit var query: Query
    private lateinit var adapter: HymnAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        query = FirebaseFirestore.getInstance().collection("hymns").document("rch")
            .collection("rchHymns")


    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRchBinding.inflate(inflater, container, false)

        val options: FirestoreRecyclerOptions<Hymn> = FirestoreRecyclerOptions.Builder<Hymn>()
            .setQuery(query, Hymn::class.java)
            .build()

        adapter = HymnAdapter(options, HymnClickListener { hymn ->
            findNavController()
                .navigate(
                    RchFragmentDirections
                        .actionHomeFragmentToContentFragment(hymn)
                )
            Timber.d("Passed Hymn is ${hymn.title}")
            Log.d("HomeFrag", "Passed Hymn 2 is ${hymn.title}")
        })
        binding.parentRecycler.adapter = adapter

//        modifyText("1.tHoly, holy, holy, 2.God Almighty!")

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}


