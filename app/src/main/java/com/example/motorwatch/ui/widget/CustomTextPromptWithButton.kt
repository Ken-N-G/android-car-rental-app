package com.example.motorwatch.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.motorwatch.ui.theme.ReducedGrey

@Composable
fun CustomTextPromptWithButton(
    startText: String,
    buttonText: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    ) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            startText,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(end = 12.dp)
        )
        CustomTextButton(
            text = buttonText,
            onClick = onClick,
        )
    }
}