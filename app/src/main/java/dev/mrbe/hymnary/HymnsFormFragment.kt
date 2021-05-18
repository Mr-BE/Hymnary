package dev.mrbe.hymnary

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class HymnsFormFragment : Fragment() {

    companion object {
        fun newInstance() = HymnsFormFragment()
    }

    private lateinit var viewModel: HymnsFormViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hymns_form_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HymnsFormViewModel::class.java)
        // TODO: Use the ViewModel
    }

}