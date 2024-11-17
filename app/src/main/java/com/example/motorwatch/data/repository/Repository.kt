package com.example.motorwatch.data.repository

import com.example.motorwatch.data.model.Claim
import com.example.motorwatch.data.model.Customer
import com.example.motorwatch.data.model.Policy
import com.example.motorwatch.data.model.Policyholder
import com.example.motorwatch.data.service.MotorWatchApi
import retrofit2.Response

class MotorWatchRepo {
    suspend fun getClaims(): Response<List<Claim>> {
        return MotorWatchApi.retrofitService.getClaims()
    }
    suspend fun getClaim(claimId: Int): Response<Claim> {
        return MotorWatchApi.retrofitService.getClaim(claimId)
    }
    suspend fun makeClaim(claim: Claim) {
        return MotorWatchApi.retrofitService.makeClaim(claim)
    }

    suspend fun getCustomer(customerId: String): Response<Customer> {
        return MotorWatchApi.retrofitService.getCustomer(customerId)
    }
    suspend fun getCustomers(): Response<List<Customer>> {
        return MotorWatchApi.retrofitService.getCustomers()
    }
    suspend fun makeCustomer(customer: Customer) {
        return MotorWatchApi.retrofitService.makeCustomer(customer)
    }
    suspend fun updateCustomer(customerId: String, customer: Customer): Response<Unit> {
        return MotorWatchApi.retrofitService.updateCustomer(customerId, customer)
    }
    suspend fun deleteCustomer(customerId: String) {
        return MotorWatchApi.retrofitService.deleteCustomer(customerId)
    }

    suspend fun getPolicies(): Response<List<Policy>> {
        return MotorWatchApi.retrofitService.getPolicies()
    }
    suspend fun getPolicy(policyId: Int): Response<Policy> {
        return MotorWatchApi.retrofitService.getPolicy(policyId)
    }

    suspend fun getPolicyholders(): Response<List<Policyholder>> {
        return MotorWatchApi.retrofitService.getPolicyholders()
    }
    suspend fun getPolicyholder(policyholderId: Int): Response<Policyholder> {
        return MotorWatchApi.retrofitService.getPolicyholder(policyholderId)
    }
    suspend fun makePolicyholder(policyholder: Policyholder) {
        return MotorWatchApi.retrofitService.makePolicyholder(policyholder)
    }
    suspend fun updatePolicyholder(policyholderId: Int, policyholder: Policyholder) {
        return MotorWatchApi.retrofitService.updatePolicyholder(policyholder, policyholderId)
    }
    suspend fun deletePolicyholder(policyholderId: Int) {
        return MotorWatchApi.retrofitService.deletePolicyholder(policyholderId)
    }
}