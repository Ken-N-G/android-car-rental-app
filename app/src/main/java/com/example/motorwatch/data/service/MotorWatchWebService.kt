package com.example.motorwatch.data.service
import com.example.motorwatch.data.model.Claim
import com.example.motorwatch.data.model.Customer
import com.example.motorwatch.data.model.Policy
import com.example.motorwatch.data.model.Policyholder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val BASE_URL = """

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface MotorWatchWebService {
    @GET("Claim")
    suspend fun getClaims(): Response<List<Claim>>
    @GET("Claim/{id}")
    suspend fun getClaim(@Path("id") claimId: Int): Response<Claim>
    @POST("Claim")
    suspend fun makeClaim(@Body claim: Claim)

    @GET("Customer/{id}")
    suspend fun getCustomer(@Path("id") customerId: String): Response<Customer>
    @GET("Customer")
    suspend fun getCustomers(): Response<List<Customer>>
    @POST("Customer")
    suspend fun makeCustomer(@Body customer: Customer)
    @PUT("Customer/{id}")
    suspend fun updateCustomer(@Path("id") customerId: String, @Body customer: Customer): Response<Unit>
    @DELETE("Customer{id}")
    suspend fun deleteCustomer(@Path("id") customerId: String)

    @GET("Policy")
    suspend fun getPolicies(): Response<List<Policy>>
    @GET("Policy/{id}")
    suspend fun getPolicy(@Path("id") policyId: Int): Response<Policy>

    @GET("Policyholder")
    suspend fun getPolicyholders(): Response<List<Policyholder>>
    @GET("Policyholder/{id}")
    suspend fun getPolicyholder(@Path("id") policyholderId: Int): Response<Policyholder>
    @POST("Policyholder")
    suspend fun makePolicyholder(@Body policyholder: Policyholder)
    @PUT("Policyholder/{id}")
    suspend fun updatePolicyholder(@Body policyholder: Policyholder, @Path("id") policyId: Int)
    @DELETE("Policyholder/{id}")
    suspend fun deletePolicyholder(@Path("id") policyId: Int)
}

object MotorWatchApi {
    val retrofitService: MotorWatchWebService by lazy {
        retrofit.create(MotorWatchWebService::class.java)
    }
}
