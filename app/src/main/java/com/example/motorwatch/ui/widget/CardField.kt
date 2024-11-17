package com.example.motorwatch.ui.widget

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CardField(
    fieldName: String,
    fieldValue: String
) {
    Text(
        fieldName,
        style = MaterialTheme.typography.headlineMedium
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        fieldValue,
        style = MaterialTheme.typography.bodyMedium
    )
}