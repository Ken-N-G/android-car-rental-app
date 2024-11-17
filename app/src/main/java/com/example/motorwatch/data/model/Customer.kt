package com.example.motorwatch.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class Customer(
    val customerId: String,
    val fullname: String,
    val email: String,
    val dateOfBirth: LocalDateTime,
    val nationality: String,
)
