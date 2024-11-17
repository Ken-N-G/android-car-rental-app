package com.example.motorwatch.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Policy(
    val policyId: Int?,
    val name: String,
    val description: String,
    val benefits: String,
    val imgUrl: String,
    val annualCoverage: Int,
    val yearlyPremium: Int,
    val deductible: Int
)
