package com.example.trainingCourses.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.trainingCourses.domain.model.AppState
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.domain.model.UseCases
import com.example.trainingCourses.presentation.fragment.Comment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            .filter { it.isNotEmpty() }
            .flatMapLatest { currentFilters ->
                useCasesDataRetrieval.getHomeCoursesUseCase(currentFilters)
            }.cachedIn(viewModelScope)

    fun getComments(courseId: Int): Flow<PagingData<Comment>> =
        useCasesDataRetrieval.getCommentsCourseUseCase(courseId).cachedIn(viewModelScope)

    fun addToFilter(
        key: String,
        value: String,
    ) {
        _appState.value =
            _appState.value.copy(
                filter = useCasesFilters.addToFilterUseCase(appState.value.filter, key, value),
            )
    }

    fun getFilterValue(key: String): String? = useCasesFilters.getFilterValueUseCase(appState.value.filter, key)

    fun updateFilters(newFilters: Map<String, String>) {
        _appState.update { currentState ->
            currentState.copy(
                filter = useCasesFilters.updateFiltersUseCase(currentState.filter, newFilters),
            )
        }
    }
    }

/*class CoursesPagingSource(
    private val coursesDao: CoursesDao,
    private val apiService: ApiService,
    private val isOnline: Boolean // Флаг, указывающий на наличие интернета
) : PagingSource<Int, Courses>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Courses> {
        return try {
            if (isOnline) {
                // Если есть интернет, удаляем данные из Room и загружаем актуальные данные из сети
                coursesDao.deleteAll()
                val response = apiService.getCourses(params.key ?: 1)
                coursesDao.insertAll(response.courses)
                LoadResult.Page(
                    data = response.courses,
                    prevKey = if (params.key == 1) null else params.key!! - 1,
                    nextKey = if (response.courses.isEmpty()) null else params.key!! + 1
                )
            } else {
                // Если нет интернета, загружаем данные из Room
                val pagingSource = coursesDao.getAllCourses()
                val loadResult = pagingSource.load(params)
                LoadResult.Page(
                    data = loadResult.data,
                    prevKey = loadResult.prevKey,
                    nextKey = loadResult.nextKey
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    // ...
    Старт приложения в режиме офлайн:
Если нет доступа в интернет, загружаем данные из Room.
Старт приложения в режиме онлайн:
Если есть доступ в интернет, удаляем данные из Room и загружаем актуальные данные из сети.
Завершение работы приложения:
Сохраняем данные в Room.
Повторный запуск приложения:
Повторяем шаги 1-3. Реализация:

@HiltViewModel
class MainViewModel @Inject constructor(
    private val coursesDao: CoursesDao,
    private val apiService: ApiService,
    private val connectivityManager: ConnectivityManager // Для проверки интернета
) : ViewModel() {

    val coursesFlow: Flow<PagingData<Courses>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            CoursesPagingSource(
                coursesDao,
                apiService,
                connectivityManager.isNetworkAvailable() // Проверка интернета
            )
        }
    ).flow.cachedIn(viewModelScope)
}

// Расширение для проверки интернета
fun ConnectivityManager.isNetworkAvailable(): Boolean {
    val network = activeNetwork ?: return false
    val capabilities = getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
}*/
