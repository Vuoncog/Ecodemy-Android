package com.kltn.ecodemy.data.repository
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.repository.TeacherHomeDataProcess
import javax.inject.Inject

class TeacherRepository @Inject constructor(
    private val teacherHomeDataProcess: TeacherHomeDataProcess
) {
    suspend fun getOwnCourse(
        ownerId: String,
        onSuccessApi: (List<Course>) -> Unit
    ) = teacherHomeDataProcess.getOwnCourse(
        ownerId = ownerId,
        onSuccessApi = onSuccessApi
    )
}