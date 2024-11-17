package com.example.motorwatch.ui.viewmodel

import android.text.method.TextKeyListener.clear
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorwatch.data.model.Customer
import com.example.motorwatch.data.model.Policy
import com.example.motorwatch.data.model.Policyholder
import com.example.motorwatch.data.repository.MotorWatchRepo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import retrofit2.Response

class PolicyListViewModel: ViewModel() {
    private var _policyListState = mutableStateOf(PolicyLisState())
    val policyListState: State<PolicyLisState> = _policyListState

    private val repo = MotorWatchRepo()

    fun GetData() {
        viewModelScope.launch {
            var policyList: ArrayList<Policy> = arrayListOf()
            val response: Response<List<Policy>> = repo.getPolicies()
            if (response.isSuccessful) {
                val policy = response.body()
                policy?.forEach { policy ->
                    policyList.add(policy)
                }
                _policyListState.value = _policyListState.value.copy(
                    policies = policyList
                )
            }
        }
    }

    fun SplitBenefitString(benefitString: String): List<String>  {
        val benefits: List<String> = benefitString.split("%")
        return benefits
    }
}

data class PolicyLisState(
    var policies: ArrayList<Policy> = arrayListOf(),
)