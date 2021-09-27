package dev.mrbe.hymnary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dev.mrbe.hymnary.databinding.FragmentRchBinding


class RchFragment : Fragment() {


    private lateinit var binding: FragmentRchBinding
    private lateinit var query: Query
    private lateinit var adapter: HymnAdapter

//    val db = requireActivity().Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        query = FirebaseFirestore.getInstance().collection("hymns").document("rch")
//            .collection("rchHymns")
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
        })
        binding.parentRecycler.adapter = adapter

//        modifyText("1.tHoly, holy, holy, 2.God Almighty!")

        //Navigate to user profile
        val navView: NavigationView? = requireActivity().findViewById(R.id.navView)
        val header: View? = navView?.getHeaderView(0)
        header?.setOnClickListener {
            //            findNavController().navigate(R.id.action_rchFragment_to_userProfileFragment)
            startActivity(Intent(context, UserActivity::class.java))

            Toast.makeText(context, "OnClick", Toast.LENGTH_SHORT).show()
        }


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


