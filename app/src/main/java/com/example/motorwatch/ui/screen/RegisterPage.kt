package com.example.motorwatch.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.motorwatch.navigation.ScreenRoutes
import com.example.motorwatch.ui.viewmodel.RegisterViewModel
import com.example.motorwatch.ui.widget.CustomBottomButton
import com.example.motorwatch.ui.widget.CustomTextPromptWithButton
import com.example.roomiesapplication.widgets.CustomTexField
import kotlinx.coroutines.launch

@Composable
fun RegisterPage(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "New Here?",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                "Enter your personal details to register an account",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 36.dp)
            )

            CustomTexField(
                fieldValue = viewModel.registrationState.value.fullnameField,
                onValueChange = { text ->
                    viewModel.OnFullnameFieldChange(text)
                },
                label = "Fullname",
                placeholderText = "Enter your fullname",
                leadingIconImageVector = Icons.Filled.AccountCircle,
                contentDescription = "Fullname icon for fullname textfield",
                errorText = viewModel.registrationState.value.fullnameErrorField,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            CustomTexField(
                fieldValue = viewModel.registrationState.value.emailField,
                onValueChange = { text ->
                    viewModel.OnEmailFieldChange(text)
                },
                label = "Email",
                placeholderText = "Enter your email",
                leadingIconImageVector = Icons.Filled.Person,
                contentDescription = "Email icon for email textfield",
                errorText = viewModel.registrationState.value.emailErrorField,
                modifier = Modifier.padding(bottom = 12.dp),
                enableSecureField = false
            )
            CustomTexField(
                fieldValue = viewModel.registrationState.value.passwordField,
                onValueChange = { text ->
                    viewModel.OnPasswordFieldChange(text)
                },
                label = "Password",
                placeholderText = "Enter your password",
                leadingIconImageVector = Icons.Filled.Lock,
                contentDescription = "Password icon for password textfield",
                errorText = viewModel.registrationState.value.passwordErrorField,
                modifier = Modifier.padding(bottom = 12.dp),
                enableSecureField = true
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        CustomTextPromptWithButton(
            startText = "Already have an account?",
            buttonText = "Login here.",
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            navController.popBackStack()
        }
        CustomBottomButton(
            text = "Register",
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            coroutineScope.launch {
                val data = viewModel.Register()
                if (data != null) {
                    Toast.makeText(
                        context,
                        viewModel.registrationState.value.emailErrorField,
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(ScreenRoutes.Home.route)
                } else {
                    Toast.makeText(
                        context,
                        viewModel.registrationState.value.emailErrorField,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun RegisterPagePreview() {
    MotorWatchTheme {
        RegisterPage()
    }
}*/
