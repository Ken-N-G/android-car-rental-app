package com.example.motorwatch.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorwatch.data.model.Customer
import com.example.motorwatch.data.model.Policyholder
import com.example.motorwatch.data.repository.MotorWatchRepo
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private var _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState

    private val repo = MotorWatchRepo()

    fun GetData() {
        viewModelScope.launch {
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid != null) {
                val response: Response<Customer> = repo.getCustomer(uid)
                if (response.isSuccessful) {
                    var fullname: String = ""
                    val customer = response.body()
                    if (customer != null) {
                        fullname = customer.fullname
                    }
                    _homeState.value = _homeState.value.copy(
                        fullname = fullname
                    )
                }
            }
        }
    }
}

data class HomeState(
    var fullname: String = "",
)