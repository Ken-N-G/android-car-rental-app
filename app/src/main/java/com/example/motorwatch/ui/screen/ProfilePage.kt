package com.example.motorwatch.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.motorwatch.navigation.ScreenRoutes
import com.example.motorwatch.ui.theme.AlertRed
import com.example.motorwatch.ui.theme.ReducedBlack
import com.example.motorwatch.ui.theme.ReducedLightGrey
import com.example.motorwatch.ui.theme.WhiteBackground
import com.example.motorwatch.ui.viewmodel.ProfileViewModel
import com.example.motorwatch.ui.viewmodel.RegisterViewModel
import com.example.motorwatch.ui.widget.BottomNavigationBar
import com.example.motorwatch.ui.widget.CustomBottomButton
import com.example.roomiesapplication.widgets.CustomTexField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        viewModel.GetCurrentUserDetails()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WhiteBackground,
                    titleContentColor = ReducedBlack,
                ),
                title = {
                },
                navigationIcon = {
                    // Put login button and profile button
                    TextButton(
                        onClick = {
                            navController.popBackStack(ScreenRoutes.Login.route, false)
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(color = AlertRed)
                                .padding(5.dp)
                        ) {
                            Text(
                                "Logout",
                                style = MaterialTheme.typography.headlineMedium,
                                color = WhiteBackground
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            Column {
                Divider(color = ReducedLightGrey, thickness = 1.dp)
                BottomNavigationBar(navController = navController)
            }
        }
    ) {
            innerPadding ->
        LazyColumn(
            modifier = Modifier
                .background(WhiteBackground)
                .fillMaxHeight()
                .fillMaxHeight()
                .absolutePadding(
                    top = innerPadding.calculateTopPadding() + 30.dp,
                    left = 20.dp,
                    right = 20.dp,
                    bottom = innerPadding.calculateBottomPadding(),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                CustomTexField(
                    fieldValue = viewModel.profileState.value.fullnameField,
                    onValueChange = {
                        viewModel.OnFullnameFieldChange(it)
                    },
                    label = "Fullname",
                    placeholderText = "Enter your fullname",
                    leadingIconImageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Fullname icon for fullname textfield",
                    errorText = viewModel.profileState.value.fullnameErrorField,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
            item {
                CustomTexField(
                    fieldValue = viewModel.profileState.value.emailField,
                    onValueChange = {
                        viewModel.OnEmailFieldChange(it)
                    },
                    enabled = false,
                    label = "Email",
                    placeholderText = "Enter your email",
                    leadingIconImageVector = Icons.Filled.Person,
                    contentDescription = "Email icon for email textfield",
                    errorText = viewModel.profileState.value.emailErrorField,
                    modifier = Modifier.padding(bottom = 12.dp),
                    enableSecureField = false
                )
            }
            item {
                CustomTexField(
                    fieldValue = viewModel.profileState.value.passwordField,
                    onValueChange = {
                        viewModel.OnPasswordFieldChange(it)
                    },
                    label = "Password",
                    placeholderText = "Enter your password",
                    leadingIconImageVector = Icons.Filled.Lock,
                    contentDescription = "Password icon for password textfield",
                    errorText = viewModel.profileState.value.passwordErrorField,
                    modifier = Modifier.padding(bottom = 12.dp),
                    enableSecureField = true
                )
            }
            item {
                CustomBottomButton(
                    text = "Update Profile",
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    coroutineScope.launch {
                        val data = viewModel.Update()
                        if (data) {
                            Toast.makeText(
                                context,
                                "Update Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate(ScreenRoutes.Home.route)
                        } else {
                            Toast.makeText(
                                context,
                                "Update Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
    MotorWatchTheme {
        ProfilePage()
    }
}*/
