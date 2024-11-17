package com.example.motorwatch.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorwatch.data.model.Customer
import com.example.motorwatch.data.model.Policyholder
import com.example.motorwatch.data.repository.MotorWatchRepo
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import retrofit2.Response

class ApplyPolicyViewModel: ViewModel() {

    private val _applyPolicyState = mutableStateOf(ApplyPolicyState())
    val applyPolicyState: State<ApplyPolicyState> = _applyPolicyState

    private val repo: MotorWatchRepo = MotorWatchRepo()

    fun VerifyPolicyApplication(): Boolean {
        return _applyPolicyState.value.licenseValid && _applyPolicyState.value.carModelValid && _applyPolicyState.value.postcodeValid
    }

    suspend fun RegisterPolicyHolder(): Boolean {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val customer: Customer
        val response: Response<Customer> = repo.getCustomer(uid ?: "")
        if (response.isSuccessful) {
            val customerContent = response.body()
            if (customerContent != null) {
                customer = customerContent
                val currentMoment: Instant = Clock.System.now()
                val nextYear = currentMoment.plus(DateTimePeriod(years = 1), TimeZone.currentSystemDefault())
                val expirationDate: LocalDateTime = nextYear.toLocalDateTime(TimeZone.currentSystemDefault())
                val policyholder: Policyholder = Policyholder(
                    policyholderId = 0,
                    licensePlate = _applyPolicyState.value.licenseField,
                    carModel = _applyPolicyState.value.carModelField,
                    postcode = _applyPolicyState.value.postcodeField.toInt(),
                    driverFullname = customer.fullname,
                    driverEmail = customer.email,
                    driverDob = customer.dateOfBirth,
                    driverNationality = customer.nationality,
                    expirationDate = expirationDate,
                )
                repo.makePolicyholder(policyholder)
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    fun OnLicensePlateChange(change: String) {
        _applyPolicyState.value = _applyPolicyState.value.copy(
            licenseField = change
        )
        validateLicense()
    }

    fun OnCarModelChange(change: String) {
        _applyPolicyState.value = _applyPolicyState.value.copy(
            carModelField = change
        )
        validateCarModel()
    }

    fun OnPostcodeFieldChange(change: String) {
        _applyPolicyState.value = _applyPolicyState.value.copy(
            postcodeField = change
        )
        validatePostCode()
    }

    private fun validateLicense() {
        val isEmpty = checkIsEmpty(
            input = _applyPolicyState.value.licenseField,
            onError = {
                _applyPolicyState.value.licenseErrorField = "You cannot leave this field empty!!"
            }
        )
        val containsSpecialChar = checkForSpecialChars(
            input = _applyPolicyState.value.licenseField,
            includeUnderline = true,
            onError = {
                _applyPolicyState.value.licenseErrorField =
                    "Your license plate cannot contain a special character!"
            }
        )
        val isValid = !isEmpty && !containsSpecialChar
        if(isValid) {
            _applyPolicyState.value.licenseErrorField = ""
        }
        _applyPolicyState.value.licenseValid = isValid
    }

    private fun validateCarModel() {
        val isEmpty = checkIsEmpty(
            input = _applyPolicyState.value.carModelField,
            onError = {
                _applyPolicyState.value.carModelErrorField = "You cannot leave this field empty!!"
            }
        )
        val containsSpecialChar = checkForSpecialChars(
            input = _applyPolicyState.value.carModelField,
            includeUnderline = true,
            onError = {
                _applyPolicyState.value.carModelErrorField =
                    "Your car model cannot contain a special character!"
            }
        )
        val isValid = !isEmpty && !containsSpecialChar
        if (isValid) {
            _applyPolicyState.value.carModelErrorField = ""
        }
        _applyPolicyState.value.carModelValid = isValid
    }

    private fun validatePostCode() {
        val isEmpty = checkIsEmpty(
            input = _applyPolicyState.value.postcodeField,
            onError = {
                _applyPolicyState.value.postcodeErrorField = "You cannot leave this field empty!"
            }
        )

        val containsSpecialChar = checkForSpecialChars(
            input = _applyPolicyState.value.postcodeField,
            onError = {
                _applyPolicyState.value.postcodeErrorField = "This field cannot have special characters!"
            },
            includeUnderline = true
        )
        val startsWith0 = checkStartOfString(
            input = _applyPolicyState.value.postcodeField,
            illegalChar = '0',
            onError = {
                _applyPolicyState.value.postcodeErrorField= "The postcode cannot start with a 0!"
            }
        )
        val isNotInteger = !isInteger(
            input = _applyPolicyState.value.postcodeField,
            onError = {
                _applyPolicyState.value.postcodeErrorField= "The postcode should be typed in the form of 57000!"
            }
        )
        val isValid = !isEmpty && !containsSpecialChar && !startsWith0 && !isNotInteger
        if(isValid) {
            _applyPolicyState.value.postcodeErrorField = ""
        }
        _applyPolicyState.value.postcodeValid = isValid
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

    private fun isInteger(
        input: String,
        onError: () -> Unit
    ): Boolean {
        val integerChars = '0'..'9'
        val isInteger = input.all { it in integerChars }
        if (!isInteger) {
            onError()
        }
        return isInteger
    }
}

data class ApplyPolicyState(
    var licenseValid: Boolean = false,
    var carModelValid: Boolean = false,
    var postcodeValid: Boolean = false,
    var licenseField: String = "",
    var carModelField: String = "",
    var postcodeField: String = "",
    var licenseErrorField: String = "",
    var carModelErrorField: String = "",
    var postcodeErrorField: String = "",
)