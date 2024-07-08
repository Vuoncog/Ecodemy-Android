package com.kltn.ecodemy.domain.viewmodels

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.fromLinkToUrlArgument
import com.kltn.ecodemy.data.api.ZalopayApi
import com.kltn.ecodemy.data.navigation.Route.Arg.COURSE_ID
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.data.repository.CourseRepository
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.payment.PaymentDetail
import com.kltn.ecodemy.domain.models.payment.ZaloMethod
import com.kltn.ecodemy.domain.models.zalo.ZaloOrderData
import com.kltn.ecodemy.domain.models.zalo.ZaloOrderResponse
import com.kltn.ecodemy.domain.repository.ZaloSDK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val zalopayApi: ZalopayApi,
    private val zaloSDK: ZaloSDK,
    private val courseRepository: CourseRepository,
    private val authenticationRepository: AuthenticationRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _paymentUiState: MutableStateFlow<PaymentUiState> =
        MutableStateFlow(PaymentUiState())
    val paymentUiState = _paymentUiState.asStateFlow()

    private val courseId = savedStateHandle.get<String>(key = COURSE_ID)
    private lateinit var course: Course
    private lateinit var user: User

    init {
        getCourse()
        getUser()
    }

    fun getCourseTitle() = course.title

    private fun getCourse() {
        viewModelScope.launch {
            if (courseId != null) {
                courseRepository.getCourse(
                    courseId = courseId,
                    onSuccessApi = {
                        course = it
                    }
                )
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            authenticationRepository.getUser {
                user = it
            }
        }
    }

    fun lessonArgWhenPayingSuccessfully(): List<String> {
        val listLessonArgs = mutableListOf<String>()
        listLessonArgs.add(course.resourceId)
        listLessonArgs.add(course.title)
        listLessonArgs.add(course.teacher.userInfo.fullName)
        listLessonArgs.add(course.lecture.sections.first().title)
        listLessonArgs.add(1.toString())
        listLessonArgs.add(course.lecture.sections.first().lessons.first().title)
        listLessonArgs.add(
            course.lecture.sections.first().lessons.first().linkVideo.fromLinkToUrlArgument()
        )
        return listLessonArgs.toList()
    }

    private fun createZaloOrder(
        amount: Int
    ) {
        viewModelScope.launch {
            Log.d("PaymentCourseTitle", course.title)
            val zaloOrderData = ZaloOrderData(
                amount = amount.toString(),
                courseTitle = course.title
            )
            val response = zalopayApi.createOrder(
                contentType = "application/x-www-form-urlencoded",
                formBody = zaloOrderData.formBody()
            )
            setZaloResponse(zaloResponseOrder = response)
            val zaloInfoOrder: Map<String, String> = mapOf(
                "Transaction ID:" to zaloOrderData.app_trans_id,
                "Description:" to zaloOrderData.description,
                "Amount:" to zaloOrderData.amount
            )
            val orderMap = _paymentUiState.value.orderMap.toMutableMap()
            orderMap[Constant.ZALO] = zaloInfoOrder
            _paymentUiState.value = _paymentUiState.value.copy(
                orderMap = orderMap
            )
            Log.d("ZaloPayResponse", response.toString())
        }
    }

    private fun setZaloResponse(zaloResponseOrder: ZaloOrderResponse) {
        _paymentUiState.value = _paymentUiState.value.copy(
            zaloResponseOrder = zaloResponseOrder
        )
    }

    fun onPaymentMethodClicked(method: String) {
        val chosenMap = _paymentUiState.value.chosenMap.toMutableMap()
        chosenMap.forEach { (t, _) ->
            chosenMap[t] = false
        }
        chosenMap[method] = true
        _paymentUiState.value = _paymentUiState.value.copy(
            chosenMap = chosenMap
        )
        if (method == Constant.ZALO) {
            createZaloOrder(course.price.convertDollarToVnd())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun purchaseWithZalo(
        activity: Activity,
        context: Context,
        onPaymentSucceeded: () -> Unit,
        onPaymentCanceled: () -> Unit,
        onPaymentError: () -> Unit,
    ) {
        val response = _paymentUiState.value.zaloResponseOrder
        when (response.return_code) {
            0 -> {
                Toast.makeText(
                    context,
                    "Creating order, please wait...",
                    Toast.LENGTH_SHORT
                ).show()
            }

            1 -> {
                _paymentUiState.value = _paymentUiState.value.copy(
                    isPurchasing = true
                )
                viewModelScope.launch {
                    zaloSDK.payOrder(
                        token = response.zp_trans_token,
                        activity = activity,
                        context = context,
                        onPaymentSucceeded = {
                            viewModelScope.launch {
                                zaloSDK.addUser(
                                    ownerId = user.ownerId,
                                    courseId = course._id
                                )
                                zaloSDK.insertPaymentToSystem(
                                    paymentDetail = PaymentDetail(
                                        paymentMethod = ZaloMethod(
                                            method = "Zalo",
                                            zaloOrderData = ZaloOrderData(
                                                amount = course.price.convertDollarToVnd()
                                                    .toString(),
                                                courseTitle = course.title
                                            )
                                        ),
                                        date = LocalDateTime.now().toString(),
                                        courseId = course._id,
                                        userId = user._id ?: "User Id not found"
                                    )
                                )
                                onPaymentSucceeded()
                            }
                        },
                        onPaymentCanceled = {
                            viewModelScope.launch {
                                onPaymentCanceled()
                            }
                        },
                        onPaymentError = {
                            viewModelScope.launch {
                                onPaymentError()
                            }
                        },
                    )
                }
            }
        }
    }

    private fun Double.convertDollarToVnd(): Int = (this * 25290).toInt()
}


data class PaymentUiState(
    val isPurchasing: Boolean = false,
    val chosenMap: Map<String, Boolean> = mapOf(
        Constant.ZALO to false,
        Constant.MOMO to false,
    ),
    val orderMap: Map<String, Map<String, String>> = mapOf(
        Constant.ZALO to mapOf(),
        Constant.MOMO to mapOf(),
    ),
    val zaloResponseOrder: ZaloOrderResponse = ZaloOrderResponse(),
)