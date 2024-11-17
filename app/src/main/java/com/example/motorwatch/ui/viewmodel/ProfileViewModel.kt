package com.example.motorwatch.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorwatch.data.model.Claim
import com.example.motorwatch.data.model.Customer
import com.example.motorwatch.data.repository.MotorWatchRepo
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import retrofit2.Response

class ProfileViewModel: ViewModel() {
    var currentCustomer: Customer = Customer(
        customerId = "",
        fullname = "",
        email = "",
        dateOfBirth = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        nationality = ""
    )

    private val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    private val repo: MotorWatchRepo = MotorWatchRepo()

    fun GetCurrentUserDetails() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        viewModelScope.launch {
            val response: Response<Customer> = repo.getCustomer(uid ?: "")
            if (response.isSuccessful) {
                val customerContent = response.body()
                if (customerContent != null) {
                    currentCustomer = customerContent
                    _profileState.value = _profileState.value.copy(
                        emailField = currentCustomer.email,
                        fullnameField = currentCustomer.fullname,
                    )
                }
            }
        }
    }

    suspend fun Update(): Boolean {
        if(_profileState.value.emailValid && _profileState.value.passwordValid && _profileState.value.fullnameValid) {
            FirebaseAuth.getInstance().currentUser?.updatePassword(_profileState.value.passwordField)
                ?.await()
            val newUserDetails = Customer(
                customerId = currentCustomer.customerId,
                fullname = _profileState.value.fullnameField,
                email = _profileState.value.emailField,
                dateOfBirth = currentCustomer.dateOfBirth,
                nationality = currentCustomer.nationality
            )
            repo.updateCustomer(currentCustomer.customerId, newUserDetails)
            return true
        } else {
            return false
        }
    }

    fun OnEmailFieldChange(change: String) {
        _profileState.value = _profileState.value.copy(
            emailField = change
        )
        validateEmail()
    }

    fun OnPasswordFieldChange(change: String) {
        _profileState.value = _profileState.value.copy(
            passwordField = change
        )
        validatePassword()
    }

    fun OnFullnameFieldChange(change: String) {
        _profileState.value = _profileState.value.copy(
            fullnameField = change
        )
        validateFullname()
    }

    private fun validateEmail() {
        val isEmpty = checkIsEmpty(
            input = _profileState.value.emailField,
            onError = {
                _profileState.value.emailErrorField = "You cannot leave this field empty!!"
            }
        )
        val isValid = !isEmpty
        if(isValid) {
            _profileState.value.emailErrorField = ""
        }
        _profileState.value.emailValid = isValid
    }

    private fun validatePassword() {
        val doesNotContainSpecialChar = checkSpecialCharInclusion(
            input = _profileState.value.passwordField,
            onError = {
                _profileState.value.passwordErrorField =
                    "Your password does not contain a special character!"
            }
        )
        val isTooShort = checkPasswordLength(
            input = _profileState.value.passwordField.length,
            onError = {
                _profileState.value.passwordErrorField = "Password is too short!"
            },
            minimumLength = 8
        )
        val isEmpty = checkIsEmpty(
            input = _profileState.value.passwordField,
            onError = {
                _profileState.value.passwordErrorField = "You cannot leave this field empty!"
            }
        )
        val hasSpaces = checkForSpaces(
            input = _profileState.value.passwordField,
            onError = {
                _profileState.value.passwordErrorField =
                    "This field contains spaces! Remove them"
            }
        )
        val isValid = !isEmpty && !doesNotContainSpecialChar && !hasSpaces && !isTooShort
        if (isValid) {
            _profileState.value.passwordErrorField = ""
        }
        _profileState.value.passwordValid = isValid
    }

    private fun validateFullname() {
        val isEmpty = checkIsEmpty(
            input = _profileState.value.fullnameField,
            onError = {
                _profileState.value.fullnameErrorField = "You cannot leave this field empty!"
            }
        )

        val containsSpecialChar = checkForSpecialChars(
            input = _profileState.value.fullnameField,
            onError = {
                _profileState.value.fullnameErrorField = "This field cannot have special characters!"
            },
            includeUnderline = true
        )
        val isTooLong = checkNameLength(
            input = _profileState.value.fullnameField.length,
            maximumLength = 200,
            onError = {
                _profileState.value.fullnameErrorField= "Your fullname is too long!"
            }
        )
        val isValid = !isEmpty && !containsSpecialChar && !isTooLong
        if(isValid) {
            _profileState.value.fullnameErrorField = ""
        }
        _profileState.value.fullnameValid = isValid
    }

    private suspend fun checkForExistingEmail(): Boolean {
        try {
            val response: Response<List<Customer>> = repo.getCustomers()
            if (response.isSuccessful) {
                val users = response.body()
                if (users != null) {
                    users.forEach { user ->
                        if(user.email == _profileState.value.emailField) {
                            _profileState.value = _profileState.value.copy(
                                emailErrorField = "This email has been taken!"
                            )
                            return true
                        }
                    }
                } else {
                    _profileState.value = _profileState.value.copy(
                        emailErrorField = "This email cannot be verified!")
                    return true
                }
            } else {
                _profileState.value = _profileState.value.copy(
                    emailErrorField = "This email cannot be verified!")
                return true
            }
        } catch (error: Exception) {
            _profileState.value = _profileState.value.copy(
                emailErrorField = "This email cannot be verified!")
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

data class ProfileState(
    var emailValid: Boolean = true,
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