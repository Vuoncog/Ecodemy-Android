package com.kltn.ecodemy

import android.Manifest
import android.app.LocaleManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.messaging.messaging
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.data.impl.PreferenceKey
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.addArgument
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.user.Role
import com.kltn.ecodemy.domain.repository.AccessToken
import com.kltn.ecodemy.domain.repository.DataStorePreferences
import com.kltn.ecodemy.domain.repository.FirebaseDataProcess
import com.kltn.ecodemy.navigation.root.RootNavigationGraph
import com.kltn.ecodemy.ui.theme.KocoTheme
import com.kltn.ecodemy.ui.theme.Primary1
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPaySDK
import java.util.Locale
import javax.inject.Inject


@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalLayoutApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @Inject
    lateinit var dataStorePreferences: DataStorePreferences

    @Inject
    lateinit var accessToken: AccessToken

    @Inject
    lateinit var firebaseDataProcess: FirebaseDataProcess

    @Inject
    lateinit var authenticationRepository: AuthenticationRepository
    private val startDes: MutableState<RequestState<String>> = mutableStateOf(RequestState.Loading)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.White.toArgb()
        super.onCreate(savedInstanceState)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        requestPostNotification()

        setLanguage()
        setUser()
        accessToken.setAccessToken()
        Firebase.messaging.subscribeToTopic("chat")

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX)

        setContent {
            navController = rememberNavController()
            KocoTheme {
                when (val state = startDes.value) {
                    is RequestState.Success -> {
                        RootNavigationGraph(
                            navController = navController,
                            startDestination = state.data
                        )
                    }

                    else -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Primary1)
                        }
                    }
                }
            }
        }
    }

    private fun setUser() {
        lifecycleScope.launch {
            authenticationRepository.refreshUser(coerce = true)
            if (authenticationRepository.isLogged()) {
                FirebaseAuth.getInstance().signInAnonymously()
            } else {
                FirebaseAuth.getInstance().signOut()
            }
            authenticationRepository.getUser {
                startDes.value = RequestState.Success(
                    data = if (it.role == Role.Teacher) {
                        Graph.TEACHER_MAIN.addArgument(
                            Graph.HOME,
                            Constant.START_DESTINATION
                        )
                    } else {
                        Graph.MAIN.addArgument(
                            Graph.HOME,
                            Constant.START_DESTINATION
                        )
                    }
                )
            }
        }

    }

    private fun setLanguage() {
        lifecycleScope.launch {
            dataStorePreferences.readData(pref = PreferenceKey.languagePref)
                .collect { tag ->
                    val langTag = tag ?: "en"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        getSystemService(
                            LocaleManager::class.java
                        ).applicationLocales = LocaleList(Locale.forLanguageTag(langTag))
                    } else {
                        val config = resources.configuration
                        val locale = Locale(langTag)
                        Locale.setDefault(locale)
                        config.setLocale(locale)

                        createConfigurationContext(config)
                        resources.updateConfiguration(config, resources.displayMetrics)
                    }
                }
        }
    }

    private fun requestPostNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasRequest = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasRequest) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        ZaloPaySDK.getInstance().onResult(intent)
    }
}