package com.example.motorwatch.ui.viewmodel

import android.text.method.TextKeyListener.clear
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorwatch.data.model.Customer
import com.example.motorwatch.data.model.Policyholder
import com.example.motorwatch.data.repository.MotorWatchRepo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import retrofit2.Response

class PoliciesViewModel: ViewModel() {
    private var _policiesState = mutableStateOf(PoliciesState())
    val policiesState: State<PoliciesState> = _policiesState

    private val repo = MotorWatchRepo()

    fun GetData() {
        viewModelScope.launch {
            var fullname = ""
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid != null) {
                val response: Response<Customer> = repo.getCustomer(uid)
                if (response.isSuccessful) {
                    val customer = response.body()
                    if (customer != null) {
                        fullname = customer.fullname
                    }
                }
            }
            if (uid != null) {
                val response2: Response<List<Policyholder>> = repo.getPolicyholders()
                if (response2.isSuccessful) {
                    var policyholders: ArrayList<Policyholder> = arrayListOf()
                    response2.body()?.forEach { policyholder ->
                        if (policyholder.driverFullname == fullname) {
                            policyholders.add(policyholder)
                        }
                    }
                    _policiesState.value = _policiesState.value.copy(
                        policyholders = policyholders
                    )
                }
            }
        }
    }
}

data class PoliciesState(
    var policyholders: ArrayList<Policyholder> = arrayListOf(),
)