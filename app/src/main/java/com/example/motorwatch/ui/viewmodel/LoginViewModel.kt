package com.example.motorwatch.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await

class LoginViewModel: ViewModel() {
    private var _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    suspend fun Login(): AuthResult? {
        return try {
            val response = Firebase.auth.signInWithEmailAndPassword(
                _loginState.value.emailField,
                _loginState.value.passwordField
            ).await()
            _loginState.value = _loginState.value.copy(
                isLoading = true
            )
            response
        } catch(error: Exception) {
            _loginState.value = _loginState.value.copy(
                isLoading = true,
                emailErrorField = error.toString()
            )
            null
        }
    }

    fun OnEmailFieldChange(change: String) {
        _loginState.value = _loginState.value.copy(
            emailField = change
        )
    }

    fun OnPasswordFieldChange(change: String) {
        _loginState.value = _loginState.value.copy(
            passwordField = change
        )
    }
}

data class LoginState(
    var emailField: String = "",
    var passwordField: String = "",
    var emailErrorField: String = "",
    var passwordErrorField: String = "",
    var isLoading: Boolean = false,
)