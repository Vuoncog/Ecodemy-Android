package com.kltn.ecodemy.navigation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.Constant.OWNER_ID
import com.kltn.ecodemy.constant.getActivity
import com.kltn.ecodemy.constant.sharedViewModel
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.data.navigation.addArgument
import com.kltn.ecodemy.data.navigation.argument
import com.kltn.ecodemy.domain.models.user.Role
import com.kltn.ecodemy.domain.viewmodels.AccountViewModel
import com.kltn.ecodemy.domain.viewmodels.ChangeAvatarViewModel
import com.kltn.ecodemy.ui.screens.account.AccountContent
import com.kltn.ecodemy.ui.screens.account.settings.changeAvatar.CapturePhoto
import com.kltn.ecodemy.ui.screens.account.settings.changeAvatar.ChangeAvatarScreen
import com.kltn.ecodemy.ui.screens.account.settings.history.SettingsHistoryScreen
import com.kltn.ecodemy.ui.screens.account.settings.language.SettingsLanguageContent
import com.kltn.ecodemy.ui.screens.account.settings.resetPassword.ResetPassword

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.accountNavigation(
    paddingValues: PaddingValues,
    accountViewModel: AccountViewModel,
    onLoginClicked: () -> Unit,
    navigateToHome: () -> Unit,
    onLogoutClicked: () -> Unit,
    showBottomBar: (Boolean) -> Unit = {},
    navController: NavHostController,
) {
    navigation(
        route = Graph.ACCOUNT,
        startDestination = Route.Account.route,
    ) {
        composable(route = Route.Account.route) {
            showBottomBar(true)
            AccountContent(
                paddingValues = paddingValues,
                onSettingClicked = {
                    showBottomBar(false)
                    Log.d("Nav", it)
                    navController.navigate(it)
                },
                onLogoutClicked = {
                    accountViewModel.logout(
                        onSuccess = onLogoutClicked
                    )
                },
                onLoginClicked = onLoginClicked,
                viewModel = accountViewModel,
            )
        }

        composable(route = Route.Setting.SettingItem.Language.settingRoute) {
            showBottomBar(false)
            SettingsLanguageContent(
                onBackClicked = {
                    showBottomBar(true)
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Route.Setting.SettingItem.ChangeAvatar.settingRoute
        ) {
            val context = LocalContext.current
            showBottomBar(false)
            val viewModel = it.sharedViewModel<ChangeAvatarViewModel>(navController = navController)
            ChangeAvatarScreen(
                onBackClicked = {
                    navController.popBackStack()
                },
                onCaptureClicked = {
                    if (!hasRequiredPermission(context)) {
                        ActivityCompat.requestPermissions(
                            context.getActivity(),
                            PERMISSIONS,
                            0
                        )
                    }
                    navController.navigate(Route.Setting.SettingItem.CapturePhoto.settingRoute)
                },
                changeAvatarViewModel = viewModel
            )
        }

        composable(route = Route.Setting.SettingItem.CapturePhoto.settingRoute) {
            showBottomBar(false)
            val viewModel = it.sharedViewModel<ChangeAvatarViewModel>(navController = navController)
            CapturePhoto(
                onTakePhoto = viewModel::assignPhoto,
                onAlreadyCapture = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Route.Setting.SettingItem.ChangePassword.settingRoute) {
            showBottomBar(false)
            ResetPassword(
                accountViewModel = accountViewModel,
                onBackClicked = {
                    showBottomBar(true)
                    navController.popBackStack()
                },
                onChangeClicked = {
                    accountViewModel.resetPassword(
                        onSucceed = {
                            showBottomBar(true)
                            navController.popBackStack()
                        }
                    )
                }
            )
        }

        composable(
            route = Route.Setting.SettingItem.PaymentHistory().settingRoute.argument(OWNER_ID),
            arguments = listOf(navArgument(OWNER_ID) { type = NavType.StringType })
        ) {
            showBottomBar(false)
            SettingsHistoryScreen(
                onBackClicked = {
                    showBottomBar(true)
                    navController.popBackStack()
                }
            )
        }
    }
}

val PERMISSIONS = arrayOf(
    Manifest.permission.CAMERA,
)

private fun hasRequiredPermission(context: Context) = PERMISSIONS.all {
    ContextCompat.checkSelfPermission(
        context, it
    ) == PackageManager.PERMISSION_GRANTED
}