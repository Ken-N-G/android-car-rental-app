package com.example.motorwatch.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class Claim(
    val claimId: Int,
    val userId: String,
    val policyId: Int,
    val claimReason: String,
    val serviceShop: String,
    val dateMade: LocalDateTime,
    val status: String
)