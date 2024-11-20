package com.example.trainingCourses.presentation.activities

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.trainingCourses.domain.ui.adapters.AppStateListener
import com.example.trainingCourses.R
import com.example.trainingCourses.databinding.ActivityMainBinding
import com.example.trainingCourses.domain.dialog.OrderByFilterDialog
import com.example.trainingCourses.domain.filter.FilterUpdater
import com.example.trainingCourses.domain.model.CourseUpdateEvent
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.domain.ui.course.CourseItemClickListener
import com.example.trainingCourses.domain.utils.ResourceStringProvider
import com.example.trainingCourses.presentation.adapters.RcViewDialogSpinnerAdapter
import com.example.trainingCourses.presentation.dialogs.DialogSpinnerHelper
import com.example.trainingCourses.presentation.viewModel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    ResourceStringProvider,
    OrderByFilterDialog,
    FilterUpdater,
    CourseItemClickListener,
    AppStateListener {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.dark)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.id_account -> {
                    Toast.makeText(this, "Account is not implemented yet", Toast.LENGTH_SHORT)
                        .show()
                    true
                }

                R.id.id_favs -> {
                    val navController = findNavController(R.id.nav_host_fragment)
                    navController.navigate(R.id.favoritesFragment)
                    true
                }

                R.id.id_home -> {
                    val navController = findNavController(R.id.nav_host_fragment)
                    navController.navigate(R.id.mainFragment)
                    true
                }

                else -> false
            }
        }

    }

    override fun showSpinnerPopup(
        anchorView: View,
        list: ArrayList<String>,
        tvSelection: TextView,
        onItemSelectedListener: RcViewDialogSpinnerAdapter.OnItemSelectedListener?,
    ) {
        DialogSpinnerHelper.showDialogSpinner(
            this,
            anchorView,
            list,
            tvSelection,
            onItemSelectedListener,
        )
    }

    override fun addToFilter(
        key: String,
        value: String,
    ) {
        viewModel.addToFilter(key, value)
    }

    override fun getStringImpl(resId: Int): String = this.getString(resId)

    override fun onCourseClick(courses: Courses) {
        val bundle = Bundle().apply {
            putParcelable(COURSE_BUNDLE, courses)
        }
        findNavController(R.id.nav_host_fragment).navigate(
            R.id.action_global_to_descriptionFragment,
            bundle
        )
    }

    override fun onFavClick(course: Courses) {
        lifecycleScope.launch {
            viewModel.toggleFavorite(course)
        }
    }

    override fun onAppStateEvent(courseEvent: (CourseUpdateEvent) -> Unit) {
        viewModel.viewModelScope.launch {
            viewModel.appState.drop(1).collectLatest { event ->
                event.courseEvent?.let { courseEvent ->
                    courseEvent(courseEvent)
                }
            }
        }
    }

    companion object {
        const val COURSE_BUNDLE = "course_bundle"
    }

}