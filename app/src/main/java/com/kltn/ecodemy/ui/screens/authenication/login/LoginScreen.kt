package com.kltn.ecodemy.ui.screens.authenication.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kltn.ecodemy.domain.viewmodels.LoginViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onBackClicked: () -> Unit,
    onForgetClicked: () -> Unit,
    onSignupClicked: () -> Unit,
    onLoginClicked: (Boolean) -> Unit,
) {
    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        LoginContent(
            paddingValues = paddingValues,
            onBackClicked = onBackClicked,
            onForgetClicked = onForgetClicked,
            onSignupClicked = onSignupClicked,
            onLoginClicked = onLoginClicked,
            loginViewModel = loginViewModel,
        )
    }
}