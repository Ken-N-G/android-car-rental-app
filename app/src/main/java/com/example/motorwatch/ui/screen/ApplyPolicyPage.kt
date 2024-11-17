package com.example.motorwatch.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.motorwatch.navigation.ScreenRoutes
import com.example.motorwatch.ui.theme.WhiteBackground
import com.example.motorwatch.ui.viewmodel.ApplyPolicyViewModel
import com.example.motorwatch.ui.viewmodel.PolicyListViewModel
import com.example.motorwatch.ui.widget.CustomBottomButton
import com.example.roomiesapplication.widgets.CustomTexField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ApplyPolicyPage(
    modifier: Modifier = Modifier,
    policyId: String,
    viewModel: ApplyPolicyViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController
) {

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                          navController.popBackStack()
                    },
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                }
                Text(
                    "Apply",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    ) {
            innerPadding ->
        Column(
            modifier = Modifier
                .background(WhiteBackground)
                .fillMaxHeight()
                .fillMaxHeight()
                .absolutePadding(
                    top = innerPadding.calculateTopPadding() + 20.dp,
                    left = 30.dp,
                    right = 30.dp,
                    bottom = 0.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            CustomTexField(
                fieldValue = viewModel.applyPolicyState.value.licenseField,
                onValueChange = {
                    viewModel.OnLicensePlateChange(it)
                },
                label = "License Plate Number",
                placeholderText = "Enter the license plate number",
                contentDescription = "",
                errorText = viewModel.applyPolicyState.value.licenseErrorField,
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomTexField(
                fieldValue = viewModel.applyPolicyState.value.carModelField,
                onValueChange = {
                    viewModel.OnCarModelChange(it)
                },
                label = "Car Model",
                placeholderText = "Enter the car model",
                contentDescription = "",
                errorText = viewModel.applyPolicyState.value.carModelErrorField,
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomTexField(
                fieldValue = viewModel.applyPolicyState.value.postcodeField,
                onValueChange = {
                    viewModel.OnPostcodeFieldChange(it)
                },
                label = "Postcode",
                placeholderText = "Enter the postcode",
                contentDescription = "",
                errorText = viewModel.applyPolicyState.value.postcodeErrorField,
            )
            Spacer(modifier = Modifier.weight(1f))
            CustomBottomButton(text = "Apply") {
                if(viewModel.VerifyPolicyApplication()) {
                    coroutineScope.launch {
                        val popBackToMainScreen = viewModel.RegisterPolicyHolder()
                        if(popBackToMainScreen) {
                            navController.popBackStack(ScreenRoutes.Home.route, false)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun ApplyPolicyPreview() {
    MotorWatchTheme {
        ApplyPolicyPage()
    }
}*/
