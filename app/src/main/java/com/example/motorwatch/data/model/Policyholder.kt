package com.example.motorwatch.data.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Policyholder(
    val policyholderId: Int,
    val licensePlate: String,
    val carModel: String,
    val postcode: Int,
    val driverFullname: String,
    val driverEmail: String,
    val driverDob: LocalDateTime,
    val driverNationality: String,
    val expirationDate: LocalDateTime
    )