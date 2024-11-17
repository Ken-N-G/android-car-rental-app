package com.example.motorwatch.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.motorwatch.data.model.Customer
import com.example.motorwatch.data.repository.MotorWatchRepo
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import retrofit2.Response

class RegisterViewModel: ViewModel() {
    private val _registrationState = mutableStateOf(RegisterState())
    val registrationState: State<RegisterState> = _registrationState

    private val repo: MotorWatchRepo = MotorWatchRepo()

    suspend fun Register(): AuthResult? {
        if(_registrationState.value.emailValid && _registrationState.value.passwordValid && _registrationState.value.fullnameValid && !checkForExistingEmail()) {
            val data = FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                _registrationState.value.emailField,
                _registrationState.value.passwordField
            ).await()
            try {
                val currentMoment: Instant = Clock.System.now()
                val localDateTime: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
                val user = Customer(
                    customerId = data?.user?.uid.toString(),
                    fullname = _registrationState.value.fullnameField,
                    email = _registrationState.value.emailField,
                    dateOfBirth = localDateTime,
                    nationality = "malaysian"
                )
                repo.makeCustomer(user)
                return data
            } catch (e: Exception) {
                Log.d("Uplaod Error", "Error = ${e} Error cause = ${e.cause}")
                _registrationState.value.emailErrorField = e.toString()
                return null
            }
        } else {
            return null
        }
    }

    fun OnEmailFieldChange(change: String) {
        _registrationState.value = registrationState.value.copy(
            emailField = change
        )
        validateEmail()
    }

    fun OnPasswordFieldChange(change: String) {
        _registrationState.value = registrationState.value.copy(
            passwordField = change
        )
        validatePassword()
    }

    fun OnFullnameFieldChange(change: String) {
        _registrationState.value = registrationState.value.copy(
            fullnameField = change
        )
        validateFullname()
    }

    private fun validateEmail() {
        val isEmpty = checkIsEmpty(
            input = _registrationState.value.emailField,
            onError = {
                _registrationState.value.emailErrorField = "You cannot leave this field empty!!"
            }
        )
        val isValid = !isEmpty
        if(isValid) {
            _registrationState.value.emailErrorField = ""
        }
        _registrationState.value.emailValid = isValid
    }

    private fun validatePassword() {
        val doesNotContainSpecialChar = checkSpecialCharInclusion(
            input = _registrationState.value.passwordField,
            onError = {
                _registrationState.value.passwordErrorField =
                    "Your password does not contain a special character!"
            }
        )
        val isTooShort = checkPasswordLength(
            input = _registrationState.value.passwordField.length,
            onError = {
                _registrationState.value.passwordErrorField = "Password is too short!"
            },
            minimumLength = 8
        )
        val isEmpty = checkIsEmpty(
            input = _registrationState.value.passwordField,
            onError = {
                _registrationState.value.passwordErrorField = "You cannot leave this field empty!"
            }
        )
        val hasSpaces = checkForSpaces(
            input = _registrationState.value.passwordField,
            onError = {
                _registrationState.value.passwordErrorField =
                    "This field contains spaces! Remove them"
            }
        )
        val isValid = !isEmpty && !doesNotContainSpecialChar && !hasSpaces && !isTooShort
        if (isValid) {
            _registrationState.value.passwordErrorField = ""
        }
        _registrationState.value.passwordValid = isValid
    }

    private fun validateFullname() {
        val isEmpty = checkIsEmpty(
            input = _registrationState.value.fullnameField,
            onError = {
                _registrationState.value.fullnameErrorField = "You cannot leave this field empty!"
            }
        )

        val containsSpecialChar = checkForSpecialChars(
            input = _registrationState.value.fullnameField,
            onError = {
                _registrationState.value.fullnameErrorField = "This field cannot have special characters!"
            },
            includeUnderline = true
        )
        val isTooLong = checkNameLength(
            input = _registrationState.value.fullnameField.length,
            maximumLength = 200,
            onError = {
                _registrationState.value.fullnameErrorField= "Your fullname is too long!"
            }
        )
        val isValid = !isEmpty && !containsSpecialChar && !isTooLong
        if(isValid) {
            _registrationState.value.fullnameErrorField = ""
        }
        _registrationState.value.fullnameValid = isValid
    }

    private suspend fun checkForExistingEmail(): Boolean {
        try {
            val response: Response<List<Customer>> = repo.getCustomers()
            if (response.isSuccessful) {
                val users = response.body()
                if (users != null) {
                    users.forEach { user ->
                        if(user.email == _registrationState.value.emailField) {
                            _registrationState.value.emailErrorField = "This email has been taken!"
                            return true
                        }
                    }
                } else {
                    _registrationState.value.emailErrorField = "This email cannot be verified!"
                    return true
                }
            } else {
                _registrationState.value.emailErrorField = "This email cannot be verified!"
                return true
            }
        } catch (error: Exception) {
            _registrationState.value.emailErrorField = "This email cannot be verified!"
            return true
        }
        return false
    }

    private fun checkForSpaces(
        input: String,
        onError: () -> Unit
    ): Boolean {
        val containsSpace = input.contains(" ")
        if (containsSpace) {
            onError()
        }
        return containsSpace
    }

    private fun checkForSpecialChars(
        input: String,
        onError: () -> Unit,
        includeUnderline: Boolean
    ): Boolean {
        val containsSpecialChar: Boolean = when (includeUnderline) {
            false -> input.contains(regex = Regex("[<>\\/\\\\%&*#\$@^?!()}{:;\"'.,|+=-]"))
            true -> input.contains(regex = Regex("[<>\\/\\\\%&*#\$@^?!()}{:;\"'.,|+=\\-_]"))
        }
        if (containsSpecialChar) {
            onError()
        }
        return containsSpecialChar
    }

    private fun checkSpecialCharInclusion(
        input: String,
        onError: () -> Unit
    ): Boolean {
        val doesNotContainSpecialChar = !input.contains(regex = Regex("[<>\\/\\\\%&*#\$@^?!()}{:;\"'.,|+=\\-_]"))
        if (doesNotContainSpecialChar) {
            onError()
        }
        return doesNotContainSpecialChar
    }

    private fun checkStartOfString(
        input: String,
        illegalChar: Char,
        onError: () -> Unit
    ): Boolean {
        val startsWithIllegalChar = input.startsWith(illegalChar)
        if (startsWithIllegalChar) {
            onError()
        }
        return startsWithIllegalChar
    }

    private fun checkIsEmpty(
        input: String,
        onError: () -> Unit
    ): Boolean {
        val isEmpty = input.isEmpty()
        if (isEmpty) {
            onError()
        }
        return isEmpty
    }

    private fun checkNameLength(
        input: Int,
        maximumLength: Int,
        onError: () -> Unit
    ): Boolean {
        val isBeyondMaximumLength = input > maximumLength
        if (isBeyondMaximumLength) {
            onError()
        }
        return isBeyondMaximumLength
    }

    private fun checkPasswordLength(
        input: Int,
        minimumLength: Int,
        onError: () -> Unit
    ): Boolean {
        val isBelowMinimumLength = input < minimumLength
        if (isBelowMinimumLength) {
            onError()
        }
        return isBelowMinimumLength
    }
}

data class RegisterState(
    var emailValid: Boolean = false,
    var passwordValid: Boolean = false,
    var fullnameValid: Boolean = false,
    var emailField: String = "",
    var fullnameField: String = "",
    var passwordField: String = "",
    var confirmPasswordField: String = "",
    var emailErrorField: String = "",
    var fullnameErrorField: String = "",
    var passwordErrorField: String = "",
    var confirmPasswordErrorField: String = "",
    var registrationSuccessFull: Boolean = false,
    var uploadingSuccessfull: Boolean = false,
)