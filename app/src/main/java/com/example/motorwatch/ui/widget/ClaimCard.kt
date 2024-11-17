package com.example.motorwatch.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.motorwatch.data.model.ClaimStatus
import com.example.motorwatch.data.model.ClaimStatusType
import com.example.motorwatch.navigation.BottomNavigationBarRoutes
import com.example.motorwatch.ui.theme.AlertRed
import com.example.motorwatch.ui.theme.ReducedBlack
import com.example.motorwatch.ui.theme.ReducedGrey
import com.example.motorwatch.ui.theme.WhiteBackground
import com.example.motorwatch.ui.theme.YellowSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClaimCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    claimId: String,
    claimDate: String,
    claimLocation: String,
    claimStatus: ClaimStatus,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = WhiteBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    claimId,
                    color = ReducedGrey,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    claimDate,
                    color = ReducedGrey,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    claimLocation,
                    color = ReducedBlack,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    claimStatus.status,
                    color = when (claimStatus.statusType) {
                        ClaimStatusType.SUBMITTED -> Color.Blue
                        ClaimStatusType.CANCELLED -> AlertRed
                        ClaimStatusType.REVIEW -> YellowSecondary
                        ClaimStatusType.REJECTED -> Color.Red
                        ClaimStatusType.APPROVED -> Color.Green
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}