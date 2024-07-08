package com.kltn.ecodemy.ui.screens.payment

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.constant.getActivity
import com.kltn.ecodemy.domain.models.payment.PaymentStatus
import com.kltn.ecodemy.domain.viewmodels.PaymentViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaymentScreen(
    paymentViewModel: PaymentViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onSuccessfulPaymentClicked: (List<String>) -> Unit,
) {
    val paymentUiState = paymentViewModel.paymentUiState.collectAsState().value
    val context = LocalContext.current
    val openDialog = remember {
        mutableStateOf(false)
    }
    val paymentStatus: MutableState<PaymentStatus> = remember {
        mutableStateOf(PaymentStatus.IDLE)
    }
    Scaffold(
        content = { paddingValues ->
            PaymentContent(
                paddingValues = paddingValues,
                onBackClicked = onBackClicked,
                orderMap = paymentUiState.orderMap,
                chosenMap = paymentUiState.chosenMap,
                onPaymentMethodClicked = paymentViewModel::onPaymentMethodClicked
            )
        },
        bottomBar = {
            PaymentBotBar(
                isPurchasing = paymentUiState.isPurchasing,
                onPurchaseClicked = {
                    Log.d("MethodChosen", whichMethodForPurchasing(paymentUiState.chosenMap))
                    paymentViewModel.purchaseWithZalo(
                        activity = context.getActivity(),
                        context = context,
                        onPaymentSucceeded = {
                            openDialog.value = true
                            paymentStatus.value =
                                PaymentStatus.SUCCESSFUL(title = paymentViewModel.getCourseTitle())
                        },
                        onPaymentCanceled = {
                            openDialog.value = true
                            paymentStatus.value = PaymentStatus.CANCELED
                        },
                        onPaymentError = {
                            openDialog.value = true
                            paymentStatus.value = PaymentStatus.ERROR
                        }
                    )
                }
            )
        }
    )
    if (openDialog.value) {
        PaymentDialog(
            openDialog = openDialog,
            paymentStatus = paymentStatus.value,
            onClicked = {
                when (paymentStatus.value) {
                    is PaymentStatus.SUCCESSFUL -> {
                        openDialog.value = !openDialog.value
                        onSuccessfulPaymentClicked(
                            paymentViewModel.lessonArgWhenPayingSuccessfully()
                        )
                    }

                    else -> {
                        openDialog.value = !openDialog.value
                        onBackClicked()
                    }
                }
            }
        )
    }
}

private fun whichMethodForPurchasing(
    chosenMap: Map<String, Boolean>,
): String {
    chosenMap.forEach { (key, _) ->
        if (chosenMap[key] == true) {
            return key
        }
    }
    return "Don't find method"
}