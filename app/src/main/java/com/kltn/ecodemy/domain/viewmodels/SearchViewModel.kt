package com.kltn.ecodemy.domain.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.data.repository.CourseRepository
import com.kltn.ecodemy.data.repository.SearchRepository
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.course.Keyword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchRepository: SearchRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val courseRepository: CourseRepository,
) : ViewModel() {
    private val _searchUiState: MutableStateFlow<RequestState<SearchUiState>> =
        MutableStateFlow(RequestState.Loading)
    val searchUiState = _searchUiState.asStateFlow()
    val searchKeyword = mutableStateOf("")
    val selectedFilterKeywords = mutableStateMapOf<String, Pair<Boolean, String>>()
    private val data: MutableState<SearchUiState> = mutableStateOf(SearchUiState())
    private val searchData = savedStateHandle.get<String>(key = Constant.SEARCH_TEXT)
    private lateinit var ownerId: String
    private lateinit var userId: String

    init {
        initData()
    }

    fun refresh() {
        initData()
    }

    fun refreshData() {
        if (this::ownerId.isInitialized) {
            getRecommendationCourses(ownerId)
        }

        if (this::userId.isInitialized) {
            getInCommonCourses(userId)
        }
    }

    private fun initData() {
        fetchData()
        viewModelScope.launch {
            authenticationRepository.refreshUser(coerce = true)
            authenticationRepository.getUser {
                if (it.ownerId.isNotBlank()) {
                    ownerId = it.ownerId
                    userId = it._id!!
                    getRecommendationCourses(it.ownerId)
                    getInCommonCourses(it._id)
                }
                getCoursesFromRecommender()
            }
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            if (searchData != null) {
                fetchSearchResult(searchData)
            }
            searchRepository.searchGetKeywords(
                onSuccessApi = { keywords ->
                    data.value = data.value.copy(allKeywords = keywords)
                    this@SearchViewModel.setData()
                }
            )
        }
    }

    private fun getCoursesFromRecommender() {
        viewModelScope.launch {
            courseRepository.getRecommenderCourses {
                data.value = data.value.copy(
                    coursesFromRecommender = it
                )
                setData()
            }
        }
    }

    private fun getRecommendationCourses(
        ownerId: String,
    ) {
        viewModelScope.launch {
            searchRepository.getRecommendCourses(
                ownerId = ownerId,
                onSuccess = {
                    data.value = data.value.copy(
                        recommendCourses = it.courses,
                        category = it.category,
                        latestRecommendationCourse = it.latestCourse
                    )
                    setData()
                }
            )
        }
    }

    private fun getInCommonCourses(
        userId: String,
    ) {
        viewModelScope.launch {
            courseRepository.getInCommonCourses(
                userId = userId,
                onSuccessApi = {
                    data.value = data.value.copy(
                        inCommonCourses = it,
                    )
                    setData()
                }
            )
        }
    }

    fun fetchSearchResult(keyword: String) {
        viewModelScope.launch {
            Log.d("SearchViewModel", "keyword: $keyword")
            _searchUiState.value = RequestState.Loading
            try {
                data.value = data.value.copy(courses = emptyList(), teachers = emptyList())
                data.value = data.value.copy(searchKeyword = keyword.substringBefore(","))
                data.value = data.value.copy(filterKeywords = keyword.substringAfter(","))
                this@SearchViewModel.setData(true)
                this@SearchViewModel.setData(true)
                searchRepository.searchCourse(
                    keyword = data.value.searchKeyword,
                    filterKeywords = data.value.filterKeywords,
                    onSuccessApi = { courses ->
                        data.value = data.value.copy(courses = courses)
                        this@SearchViewModel.setData()
                    }
                )
                searchRepository.searchTeacher(
                    keyword = data.value.searchKeyword,
                    onSuccessApi = { teachers ->
                        data.value = data.value.copy(teachers = teachers)
                    }
                )
                if (data.value.courses.isNotEmpty()) {
                    val teachersFromCourses = data.value.courses.map { it.teacher }
                    data.value =
                        data.value.copy(teachers = (data.value.teachers + teachersFromCourses).distinct())
                    this@SearchViewModel.setData()
                }
            } catch (e: Exception) {
                Log.d("SearchViewModel", "fetchSearchResult: ${e.message}")
            }
        }
    }

    private fun setData(keepLoading: Boolean = false) {
        if (keepLoading) return
        if (data.value != SearchUiState()) {
            _searchUiState.value = RequestState.Success(
                data = data.value
            )
        } else {
            _searchUiState.value = RequestState.Idle
        }
    }

    fun resetFilterKeywords() {
        data.value = data.value.copy(filterKeywords = "")
    }
}

data class SearchUiState(
    val searchKeyword: String = "",
    val filterKeywords: String = "",
    val courses: List<Course> = emptyList(),
    val inCommonCourses: List<Course> = emptyList(),
    val recommendCourses: List<Course> = emptyList(),
    val category: List<String> = emptyList(),
    val latestRecommendationCourse: String? = null,
    val coursesFromRecommender: List<Course> = emptyList(),
    val teachers: List<User> = emptyList(),
    val allKeywords: List<Keyword> = emptyList()
)