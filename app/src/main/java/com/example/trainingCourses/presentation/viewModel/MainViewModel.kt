package com.example.trainingCourses.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.trainingCourses.data.local.DatabaseModule
import com.example.trainingCourses.domain.model.AppState
import com.example.trainingCourses.domain.model.Comment
import com.example.trainingCourses.domain.model.CourseUpdateEvent
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.domain.model.UseCases
import com.example.trainingCourses.domain.utils.CourseMappers.toFavoriteCourseEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val useCasesDataRetrieval: UseCases.DataRetrieval,
    private val useCasesFilters: UseCases.Filters,
) : ViewModel() {
    companion object {
        const val PAGE_SIZE = 5
    }

    private val _appState = MutableStateFlow(AppState())
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val homeCoursesData: Flow<PagingData<Courses>> =
        appState
            .map { it.filter }
            .distinctUntilChanged()
            .filter { it.isNotEmpty() }
            .flatMapLatest { currentFilters ->
                useCasesDataRetrieval.getHomeCoursesUseCase(currentFilters)
            }.cachedIn(viewModelScope)

    fun getComments(courseId: Int): Flow<PagingData<Comment>> =
        useCasesDataRetrieval.getCommentsCourseUseCase(courseId).cachedIn(viewModelScope)

    val favoriteCoursesData: Flow<PagingData<Courses>> =
        useCasesDataRetrieval.getFavoriteCoursesUseCase().cachedIn(viewModelScope)


    suspend fun toggleFavorite(course: Courses) {

        val favoriteCoursesDao = DatabaseModule.provideFavoriteCoursesDao()
        val existingCourse = favoriteCoursesDao.getCourseById(course.id!!)
        if (course.isFavorite) {
            favoriteCoursesDao.delete(course.id)
        } else {
            if (existingCourse == null) {
                favoriteCoursesDao.insert(course.toFavoriteCourseEntity())
            }
        }

        val updatedCourse = course.copy(isFavorite = !course.isFavorite)

        _appState.value =
            _appState.value.copy(courseEvent = CourseUpdateEvent.FavUpdated(updatedCourse))
    }

    fun addToFilter(
        key: String,
        value: String,
    ) {
        _appState.value =
            _appState.value.copy(
                filter = useCasesFilters.addToFilterUseCase(appState.value.filter, key, value),
            )
    }

    fun getFilterValue(key: String): String? =
        useCasesFilters.getFilterValueUseCase(appState.value.filter, key)

    fun updateFilters(newFilters: Map<String, String>) {
        _appState.update { currentState ->
            currentState.copy(
                filter = useCasesFilters.updateFiltersUseCase(currentState.filter, newFilters),
            )
        }
    }
}

