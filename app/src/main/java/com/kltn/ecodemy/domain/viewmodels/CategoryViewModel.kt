package com.kltn.ecodemy.domain.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.data.navigation.Route.Arg.CATEGORY_KEYWORD
import com.kltn.ecodemy.data.navigation.Route.Arg.CATEGORY_TYPE
import com.kltn.ecodemy.data.repository.CourseRepository
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.course.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val courseRepository: CourseRepository,
) : ViewModel() {

    private val _categoryUiState = MutableStateFlow(CategoryUiState())
    val categoryUiState = _categoryUiState.asStateFlow()

    private val _courseList: MutableStateFlow<RequestState<List<Course>>> =
        MutableStateFlow(RequestState.Loading)
    val courseList = _courseList.asStateFlow()

    init {
        setTypeAndKeyword()
        getCourseWithCategory()
    }

    private fun setTypeAndKeyword() {
        _categoryUiState.value = _categoryUiState.value.copy(
            type = savedStateHandle.get<String>(CATEGORY_TYPE) ?: "Category",
            keyword = savedStateHandle.get<String>(CATEGORY_KEYWORD) ?: "",
        )
    }

    private fun getCourseWithCategory() {
        val category = _categoryUiState.value.keyword
        viewModelScope.launch {
            courseRepository.getCourseWithCategory(category,
                onError = {
                    _courseList.value = RequestState.Error(error = it.fillInStackTrace())
                },
                onSuccessApi = {
                    if (it.isEmpty()) {
                        _courseList.value = RequestState.Idle
                    } else {
                        _courseList.value = RequestState.Success(it)
                    }
                }
            )
        }
    }
}

data class CategoryUiState(
    val type: String = "",
    val keyword: String = "",
)