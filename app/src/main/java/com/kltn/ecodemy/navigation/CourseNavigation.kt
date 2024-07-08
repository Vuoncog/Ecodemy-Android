package com.kltn.ecodemy.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.OWNER_ID
import com.kltn.ecodemy.constant.Constant.START_DESTINATION
import com.kltn.ecodemy.constant.lessonIndex
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.data.navigation.Route.Arg.COURSE_ID
import com.kltn.ecodemy.data.navigation.Route.Arg.LESSON_ID
import com.kltn.ecodemy.data.navigation.Route.Arg.RESOURCE_PDF_URL
import com.kltn.ecodemy.data.navigation.Route.Arg.SECTION_ID
import com.kltn.ecodemy.data.navigation.Route.Arg.STUDENT_ID
import com.kltn.ecodemy.data.navigation.addArgument
import com.kltn.ecodemy.data.navigation.argument
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.screens.course.detail.CourseScreen
import com.kltn.ecodemy.ui.screens.course.register.RegisterCourseScreen
import com.kltn.ecodemy.ui.screens.course.review.ReviewScreen
import com.kltn.ecodemy.ui.screens.lesson.LessonScreen
import com.kltn.ecodemy.ui.screens.payment.PaymentScreen
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito
import com.rajat.pdfviewer.compose.PdfRendererViewCompose
import kotlinx.coroutines.FlowPreview
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@FlowPreview
@ExperimentalMaterial3Api
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.courseNavigation(
    navController: NavHostController,
) {
    navigation(
        route = Graph.COURSE.argument(type = COURSE_ID),
        startDestination = Route.Course.route,
        arguments = listOf(navArgument(COURSE_ID) { type = NavType.StringType })
    ) {
        composable(
            route = Route.Course.route
        ) { entry ->
            val parentEntry =
                remember(entry) { navController.getBackStackEntry(Graph.COURSE.argument(type = COURSE_ID)) }
            val courseId = parentEntry.arguments?.getString(COURSE_ID) ?: "dont find"
            Log.d("ArgCourseId", courseId)

            CourseScreen(
                onBackClicked = { navController.popBackStack() },
                onPurchasedClicked = {
                    navController.navigate(
                        Route.Payment.paymentArgs(
                            courseId = courseId,
                        ),
                    )
                },
                onLessonItemClicked = { sectionId, lessonId ->
                    Route.Lesson.navigateLesson(
                        navController = navController,
                        courseId = courseId,
                        sectionId = sectionId,
                        lessonId = lessonId
                    )
                },
                onRegisterClicked = {
                    navController.navigate(
                        Route.RegisterForm.registerFormArgs(
                            courseId = courseId
                        )
                    )
                },
                onChatToConsultant = {
                    navController.navigate(
                        Route.Chat.messagesArgument("Ecodemy", "ecodemy.png")
                    )
                },
                onRatedClicked = { studentId ->
                    navController.navigate(
                        Route.Review.reviewArgs(courseId, studentId)
                    )
                },
                onLoginClicked = {
                    navController.navigate(Route.Authentication.LOGIN)
                }
            )
        }

        composable(route = Route.RegisterForm.registerFormRoute()) {
            RegisterCourseScreen(onBackClicked = { navController.popBackStack() },
                onSuccessfullyRegister = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Route.Payment.paymentRoute(),
            arguments = listOf(
                navArgument(COURSE_ID) { type = NavType.StringType },
            )
        ) { entry ->
            val courseId = entry.arguments?.getString(COURSE_ID) ?: "don't find course id"
            PaymentScreen(
                onBackClicked = { navController.popBackStack() },
                onSuccessfulPaymentClicked = {
                    Route.Lesson.navigateLesson(
                        navController = navController,
                        courseId = courseId,
                        sectionId = 1,
                        lessonId = (1).lessonIndex(1),
                    )
                }
            )
        }

        composable(
            route = Route.Lesson.lessonRoute(),
            arguments = listOf(
                navArgument(COURSE_ID) { type = NavType.StringType },
                navArgument(SECTION_ID) { type = NavType.IntType },
                navArgument(LESSON_ID) { type = NavType.IntType },
            )
        ) { entry ->
            val courseId = entry.arguments?.getString(COURSE_ID) ?: "don't find course id"
            LessonScreen(
                onBackClicked = {
                    navController.navigate(
                        Graph.MAIN.addArgument(
                            Graph.LEARNING,
                            START_DESTINATION
                        )
                    )
                },
                onResourcesClick = {
                    val encode = URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
                    navController.navigate(Route.Lesson.resourcesArg(encode))
                },
                onLessonItemClicked = { sectionId, lessonId ->
                    Log.d("ArgSection", sectionId.toString())
                    Log.d("ArgLesson", lessonId.toString())
                    Route.Lesson.navigateLesson(
                        navController = navController,
                        courseId = courseId,
                        sectionId = sectionId,
                        lessonId = lessonId
                    )
                }
            )
        }

        composable(
            route = Route.Review.reviewRoute(),
            arguments = listOf(
                navArgument(COURSE_ID) { type = NavType.StringType },
                navArgument(STUDENT_ID) { type = NavType.StringType })
        ) {
            ReviewScreen(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Route.Lesson.resourcesRoute(),
            arguments = listOf(
                navArgument(RESOURCE_PDF_URL) { type = NavType.StringType },
            )
        ) {
            val arg = it.arguments?.getString(RESOURCE_PDF_URL) ?: ""
            Log.d("PDF", arg)
            Scaffold(
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                                    contentDescription = null
                                )
                            }
                        },
                        title = {
                            EcodemyText(
                                format = Nunito.Subtitle1,
                                data = "Resources.Pdf",
                                color = Neutral1
                            )
                        })
                }
            ) { paddingValues ->
                PdfRendererViewCompose(
                    modifier = Modifier.padding(paddingValues),
                    url = arg,
                    lifecycleOwner = LocalLifecycleOwner.current
                )
            }
        }
    }
}