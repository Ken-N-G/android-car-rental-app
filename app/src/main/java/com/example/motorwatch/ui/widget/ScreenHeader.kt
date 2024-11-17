package com.example.motorwatch.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motorwatch.ui.theme.ReducedGrey

@Composable
fun MainScreenHeader(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String = "",
) {
    Column(modifier = modifier) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            subTitle,
            color = ReducedGrey,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}