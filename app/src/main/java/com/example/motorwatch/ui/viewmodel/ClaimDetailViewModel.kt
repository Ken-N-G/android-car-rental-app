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

class ClaimDetailViewModel: ViewModel() {
    private var _claimDetailState = mutableStateOf(ClaimDetailState())
    val claimDetailState: State<ClaimDetailState> = _claimDetailState

    private val repo = MotorWatchRepo()

    fun GetData(id: Int) {
        viewModelScope.launch {
            val claim: Claim?
            val policyholder: Policyholder?
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid != null) {
                val response: Response<Claim> = repo.getClaim(id)
                if (response.isSuccessful) {
                    claim = response.body()
                    if (claim != null) {
                        val response: Response<Policyholder> = repo.getPolicyholder(claim.policyId)
                        if (response.isSuccessful) {
                            policyholder = response.body()
                            if (policyholder != null) {
                                _claimDetailState.value = _claimDetailState.value.copy(
                                    claimId = claim.claimId,
                                    userId = claim.userId,
                                    policyId = claim.policyId,
                                    claimReason = claim.claimReason,
                                    serviceShop = claim.serviceShop,
                                    dateMade = claim.dateMade,
                                    status = claim.status,

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
        }
    }
}

data class ClaimDetailState(
    var claimId: Int = 0,
    var userId: String = "",
    var policyId: Int = 0,
    var claimReason: String ="",
    var serviceShop: String = "",
    var dateMade: LocalDateTime? = null,
    var status: String = "",

    var policyholderId: Int = 0,
    var licensePlate: String = "",
    var carModel: String = "",
    var postcode: Int = 0,
)