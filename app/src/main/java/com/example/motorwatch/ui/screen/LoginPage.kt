package com.example.motorwatch.ui.screen

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.motorwatch.navigation.ScreenRoutes
import com.example.motorwatch.ui.theme.MotorWatchTheme
import com.example.motorwatch.ui.viewmodel.LoginViewModel
import com.example.motorwatch.ui.widget.CustomBottomButton
import com.example.motorwatch.ui.widget.CustomTextPromptWithButton
import com.example.roomiesapplication.widgets.CustomTexField
import kotlinx.coroutines.launch

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
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
                "Welcome User",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                "Enter your login details to continue",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 36.dp)
            )

            CustomTexField(
                fieldValue = viewModel.loginState.value.emailField,
                onValueChange = { text ->
                    viewModel.OnEmailFieldChange(text)
                },
                label = "E-mail",
                placeholderText = "Enter your e-mail",
                leadingIconImageVector = Icons.Filled.Person,
                contentDescription = "E-mail icon for e-mail textfield",
                errorText = viewModel.loginState.value.emailErrorField,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            CustomTexField(
                fieldValue = viewModel.loginState.value.passwordField,
                onValueChange = { text ->
                    viewModel.OnPasswordFieldChange(text)
                },
                label = "Password",
                placeholderText = "Enter your password",
                leadingIconImageVector = Icons.Filled.Lock,
                contentDescription = "Password icon for password textfield",
                errorText = "",
                enableSecureField = true
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        CustomTextPromptWithButton(
            startText = "Don't have an account?",
            buttonText = "Register here.",
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            navController.navigate(ScreenRoutes.Register.route)
        }
        CustomBottomButton(
            text = "Login",
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            coroutineScope.launch {
                val response = viewModel.Login()

                if (response != null) {
                    Toast.makeText(
                        context,
                        viewModel.loginState.value.emailErrorField,
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(ScreenRoutes.Home.route)
                } else {
                    Toast.makeText(
                        context,
                        viewModel.loginState.value.emailErrorField,
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
fun LoginPagePreview() {
    MotorWatchTheme {
        LoginPage()
    }
}*/
