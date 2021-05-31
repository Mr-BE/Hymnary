package dev.mrbe.hymnary.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

//handle firebase auth state
class AuthViewModel : ViewModel() {
    enum class AuthenticationState {
        AUTHENTICATED,
        UNAUTHENTICATED,
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }
}