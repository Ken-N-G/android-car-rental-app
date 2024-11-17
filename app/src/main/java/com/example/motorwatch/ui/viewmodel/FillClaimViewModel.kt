package com.example.motorwatch.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorwatch.data.model.Claim
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

class FillClaimViewModel: ViewModel() {

    var policyholderId: Int = 0

    private val _fillClaimState = mutableStateOf(FillClaimState())
    val fillClaimState: State<FillClaimState> = _fillClaimState

    private val repo: MotorWatchRepo = MotorWatchRepo()

    fun VerifyClaim(): Boolean {
        return _fillClaimState.value.reasonValid && _fillClaimState.value.serviceValid
    }

    fun GetPolicyholderId(id: Int) {
        policyholderId = id
    }

    suspend fun MakeClaim(): Boolean {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val customer: Customer
        val response: Response<Customer> = repo.getCustomer(uid ?: "")
        if (response.isSuccessful) {
            val customerContent = response.body()
            if (customerContent != null) {
                customer = customerContent
                val currentMoment: Instant = Clock.System.now()
                val currentDateTime: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
                val claim: Claim = Claim(
                    claimId = 0,
                    userId = customer.customerId,
                    policyId = policyholderId,
                    claimReason = _fillClaimState.value.reasonField,
                    serviceShop = _fillClaimState.value.serviceField,
                    dateMade = currentDateTime,
                    status = "submitted"
                )
                repo.makeClaim(claim)
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    fun OnReasonChange(change: String) {
        _fillClaimState.value = _fillClaimState.value.copy(
            reasonField = change
        )
        validateReason()
    }

    fun OnServiceChange(change: String) {
        _fillClaimState.value = _fillClaimState.value.copy(
            serviceField = change
        )
        validateService()
    }

    private fun validateService() {
        val isEmpty = checkIsEmpty(
            input = _fillClaimState.value.serviceField,
            onError = {
                _fillClaimState.value.serviceErrorField = "You cannot leave this field empty!!"
            }
        )
        val containsSpecialChar = checkForSpecialChars(
            input = _fillClaimState.value.serviceField,
            includeUnderline = true,
            onError = {
                _fillClaimState.value.serviceErrorField =
                    "This field cannot contain a special character!"
            }
        )
        val isValid = !isEmpty && !containsSpecialChar
        if(isValid) {
            _fillClaimState.value.serviceErrorField = ""
        }
        _fillClaimState.value.serviceValid = isValid
    }

    private fun validateReason() {
        val isEmpty = checkIsEmpty(
            input = _fillClaimState.value.reasonField,
            onError = {
                _fillClaimState.value.reasonErrorField = "You cannot leave this field empty!!"
            }
        )
        val containsSpecialChar = checkForSpecialChars(
            input = _fillClaimState.value.reasonField,
            includeUnderline = true,
            onError = {
                _fillClaimState.value.reasonErrorField =
                    "This field cannot contain a special character!"
            }
        )
        val isValid = !isEmpty && !containsSpecialChar
        if (isValid) {
            _fillClaimState.value.reasonErrorField = ""
        }
        _fillClaimState.value.reasonValid = isValid
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

data class FillClaimState(
    var reasonValid: Boolean = false,
    var serviceValid: Boolean = false,
    var reasonField: String = "",
    var serviceField: String = "",
    var reasonErrorField: String = "",
    var serviceErrorField: String = "",
)