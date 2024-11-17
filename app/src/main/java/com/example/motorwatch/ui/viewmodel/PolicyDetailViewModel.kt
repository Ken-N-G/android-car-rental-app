package com.example.motorwatch.ui.viewmodel

import android.text.method.TextKeyListener.clear
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorwatch.data.model.Claim
import com.example.motorwatch.data.model.Customer
import com.example.motorwatch.data.model.Policyholder
import com.example.motorwatch.data.repository.MotorWatchRepo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import retrofit2.Response

class PolicyDetailViewModel: ViewModel() {
    private var _policyDetailState = mutableStateOf(PolicyDetailState())
    val policyDetailState: State<PolicyDetailState> = _policyDetailState

    private val repo = MotorWatchRepo()

    fun GetData(id: Int) {
        viewModelScope.launch {
            val policyholder: Policyholder?
            val response: Response<Policyholder> = repo.getPolicyholder(id)
            if (response.isSuccessful) {
                policyholder = response.body()
                if (policyholder != null) {
                    _policyDetailState.value = _policyDetailState.value.copy(
                        policyholderId = policyholder.policyholderId,
                        licensePlate = policyholder.licensePlate,
                        carModel = policyholder.carModel,
                        postcode = policyholder.postcode
                    )
                }
            }
        }
    }
}

data class PolicyDetailState(
    var policyholderId: Int = 0,
    var licensePlate: String = "",
    var carModel: String = "",
    var postcode: Int = 0,
)