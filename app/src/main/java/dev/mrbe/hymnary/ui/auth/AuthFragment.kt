package dev.mrbe.hymnary.ui.auth

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import dev.mrbe.hymnary.MainActivity
import dev.mrbe.hymnary.R
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import timber.log.Timber

private val SIGN_IN_REQUEST_CODE = 1030

class AuthFragment : Fragment() {
    companion object {
        fun newInstance() = AuthFragment()
    }

    private val authViewModel: AuthViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        launchSignInFlow()

        authViewModel.authenticationState.observe(viewLifecycleOwner, { authenticationState ->
            when (authenticationState) {
                AuthViewModel.AuthenticationState.AUTHENTICATED -> startActivity(
                    Intent(context, MainActivity::class.java)
                )
                AuthViewModel.AuthenticationState.UNAUTHENTICATED -> Timber.e("Not authenticated!")
                else -> Timber.e(
                    "New $authenticationState state that doesn't require any UI change"
                )
            }
        })

        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    private fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), SIGN_IN_REQUEST_CODE
        )

//        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}