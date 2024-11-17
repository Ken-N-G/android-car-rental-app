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
import retrofit2.Response

class ClaimViewModel: ViewModel() {
    private var _claimState = mutableStateOf(ClaimSate())
    val claimSate: State<ClaimSate> = _claimState

    private val repo = MotorWatchRepo()

    fun GetData() {
        viewModelScope.launch {
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid != null) {
                val response2: Response<List<Claim>> = repo.getClaims()
                if (response2.isSuccessful) {
                    var claims: ArrayList<Claim> = arrayListOf()
                    response2.body()?.forEach { claim ->
                        if (claim.userId == uid) {
                            claims.add(claim)
                        }
                    }
                    _claimState.value = _claimState.value.copy(
                        claims = claims
                    )
                }
            }
        }
    }
}

data class ClaimSate(
    var claims: ArrayList<Claim> = arrayListOf(),
)