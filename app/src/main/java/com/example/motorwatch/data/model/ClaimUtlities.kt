package com.example.motorwatch.data.model

enum class ClaimStatusType {
    SUBMITTED, CANCELLED, REVIEW, REJECTED, APPROVED
}

sealed class ClaimStatus(
    val status: String,
    val statusType: ClaimStatusType
) {
    object Submitted: ClaimStatus("Submitted", ClaimStatusType.SUBMITTED)
    object Review: ClaimStatus("Review in-progress", ClaimStatusType.REVIEW)
    object Rejected: ClaimStatus("Rejected", ClaimStatusType.REJECTED)
    object Approved: ClaimStatus("Approved", ClaimStatusType.APPROVED)
}