package com.kltn.ecodemy.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.sharedViewModel
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.data.navigation.addArgument
import com.kltn.ecodemy.domain.viewmodels.ForgetViewModel
import com.kltn.ecodemy.domain.viewmodels.LoginViewModel
import com.kltn.ecodemy.domain.viewmodels.SignupViewModel
import com.kltn.ecodemy.ui.screens.authenication.forget.ForgetScreen
import com.kltn.ecodemy.ui.screens.authenication.forget.NewPasswordScreen
import com.kltn.ecodemy.ui.screens.authenication.login.LoginScreen
import com.kltn.ecodemy.ui.screens.authenication.signup.SignupScreen
import com.kltn.ecodemy.ui.screens.authenication.verification.OTPForgetScreen
import com.kltn.ecodemy.ui.screens.authenication.verification.OTPScreen
import com.kltn.ecodemy.ui.screens.authenication.verification.OTPSuccess
import kotlinx.coroutines.FlowPreview

@FlowPreview
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = Route.Authentication.LOGIN,
    ) {
        composable(Route.Authentication.LOGIN) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                loginViewModel = loginViewModel,
                onBackClicked = {
                    navController.popBackStack()
                },
                onForgetClicked = {
                    navController.navigate(Route.Authentication.FORGET)
                },
                onSignupClicked = {
                    navController.navigate(Route.Authentication.SIGNUP) {
                        popUpTo(Route.Authentication.LOGIN) {
                            inclusive = true
                        }
                    }
                },
                onLoginClicked = {
                    if (it) {
                        navController.navigate(
                            Graph.TEACHER_MAIN.addArgument(
                                Graph.ACCOUNT,
                                Constant.START_DESTINATION
                            )
                        )
                    } else {
                        navController.navigate(
                            Graph.MAIN.addArgument(
                                Graph.ACCOUNT,
                                Constant.START_DESTINATION
                            )
                        )
                    }
                }
            )
        }
        composable(Route.Authentication.SIGNUP) {
            val signupViewModel = it.sharedViewModel<SignupViewModel>(navController = navController)
            SignupScreen(
                signupViewModel = signupViewModel,
                onBackClicked = {
                    navController.navigate(Route.Authentication.LOGIN) {
                        popUpTo(Route.Authentication.SIGNUP) {
                            inclusive = true
                        }
                    }
                },
                onLoginClicked = {
                    navController.navigate(Route.Authentication.LOGIN) {
                        popUpTo(Route.Authentication.SIGNUP) {
                            inclusive = true
                        }
                    }
                },
                onSignupClicked = {
                    navController.navigate(Route.Authentication.OTP)
//                    navController.navigate(Route.Authentication.LOGIN) {
//                        popUpTo(Route.Authentication.SIGNUP) {
//                            inclusive = true
//                        }
//                    }
                }
            )
        }

        composable(Route.Authentication.OTP) {
            val signupViewModel = it.sharedViewModel<SignupViewModel>(navController = navController)
            OTPScreen(
                signupViewModel = signupViewModel,
                onVerifyClicked = {
                    navController.navigate(Route.Authentication.SIGN_UP_SUCCESS)
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.Authentication.SUCCESS,
            arguments = listOf(
                navArgument("otp") { type = NavType.BoolType }
            )) {
            val arg = it.arguments?.getBoolean("otp") ?: true
            OTPSuccess(
                arg = arg,
                onSuccessClicked = {
                    navController.navigate(Route.Authentication.LOGIN) {
                        popUpTo(Route.Authentication.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Route.Authentication.FORGET) {
            val forgetViewModel = it.sharedViewModel<ForgetViewModel>(navController = navController)
            ForgetScreen(
                forgetViewModel = forgetViewModel,
                onFindClicked = {
                    navController.navigate(Route.Authentication.OTP_FORGET)
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.Authentication.OTP_FORGET) {
            val forgetViewModel = it.sharedViewModel<ForgetViewModel>(navController = navController)
            OTPForgetScreen(
                forgetViewModel = forgetViewModel,
                onVerifyClicked = {
                    navController.navigate(Route.Authentication.CHANGE_NEW_PASSWORD)
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.Authentication.CHANGE_NEW_PASSWORD) {
            val forgetViewModel = it.sharedViewModel<ForgetViewModel>(navController = navController)
            NewPasswordScreen(
                forgetViewModel = forgetViewModel,
                onChangeClicked = {
                    navController.navigate(Route.Authentication.FORGET_SUCCESS) {
                        popUpTo(Route.Authentication.LOGIN) {
                            inclusive = false
                        }
                    }
                },
                onBackClicked = {
                    navController.navigate(Route.Authentication.FORGET) {
                        popUpTo(Route.Authentication.OTP_FORGET) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}