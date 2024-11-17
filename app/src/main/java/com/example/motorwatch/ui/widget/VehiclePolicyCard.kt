package com.example.motorwatch.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.motorwatch.ui.theme.ReducedBlack
import com.example.motorwatch.ui.theme.WhiteBackground
import com.example.motorwatch.ui.theme.YellowSecondary

@Composable
fun VehiclePolicyCard(
    modifier: Modifier = Modifier,
    width: Dp,
    vehicleIdentificationNumber: String,
    policyName: String,
    carModel: String,
    policyId: String,
    policyExpirationDate: String,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .width(width)
            .defaultMinSize(minHeight = 180.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = YellowSecondary)
            .padding(horizontal = 10.dp, vertical = 20.dp)
    ) {
        Text(
            vehicleIdentificationNumber,
            style = MaterialTheme.typography.headlineLarge
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    "Policy",
                    color = WhiteBackground,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    policyName,
                    color = ReducedBlack,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(modifier = Modifier.width(140.dp)) {
                Text(
                    "Car Model",
                    color = WhiteBackground,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    carModel,
                    color = ReducedBlack,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    "Policy No.",
                    color = WhiteBackground,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    policyId,
                    color = ReducedBlack,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(modifier = Modifier.width(140.dp)) {
                Text(
                    "Exp. Date",
                    color = WhiteBackground,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    policyExpirationDate,
                    color = ReducedBlack,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}