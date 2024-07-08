package com.kltn.ecodemy.ui.screens.authenication.signup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kltn.ecodemy.constant.authBackground
import com.kltn.ecodemy.domain.viewmodels.SignupViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignupScreen(
    onBackClicked: () -> Unit,
    onLoginClicked: () -> Unit,
    signupViewModel: SignupViewModel,
    onSignupClicked: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .authBackground(),
        containerColor = Color.White
    ) { paddingValues ->
        SignupContent(
            paddingValues = paddingValues,
            onBackClicked = onBackClicked,
            onLoginClicked = onLoginClicked,
            signupViewModel = signupViewModel,
            onSignupClicked = onSignupClicked
        )
    }
}