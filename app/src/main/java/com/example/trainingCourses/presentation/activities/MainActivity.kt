package com.example.trainingCourses.presentation.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.trainingCourses.R
import com.example.trainingCourses.databinding.ActivityMainBinding
import com.example.trainingCourses.domain.api.ApiService.Companion.ORDER_QUERY_PARAM
import com.example.trainingCourses.domain.dialog.OrderByFilterDialog
import com.example.trainingCourses.domain.filter.FilterUpdater
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.domain.ui.course.CourseItemClickListener
import com.example.trainingCourses.domain.utils.ResourceStringProvider
import com.example.trainingCourses.presentation.adapters.RcViewDialogSpinnerAdapter
import com.example.trainingCourses.presentation.dialogs.DialogSpinnerHelper
import com.example.trainingCourses.presentation.viewModel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    ResourceStringProvider,
    OrderByFilterDialog,
    FilterUpdater,
    CourseItemClickListener {
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
                    Toast.makeText(this, "Account is not implemented yet", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.id_favs -> {
                    Toast.makeText(this, "Favs is not implemented yet", Toast.LENGTH_SHORT).show()
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
        Timber.d("${viewModel.getFilterValue(ORDER_QUERY_PARAM)}")
        val temp_map = mapOf(key to value)
        viewModel.updateFilters(temp_map)
        Timber.d("${viewModel.getFilterValue(ORDER_QUERY_PARAM)}")
    }

    override fun getStringImpl(resId: Int): String = this.getString(resId)

    override fun onCourseClick(courses: Courses) {
        val bundle = Bundle().apply {
            putParcelable("course", courses)
        }
        findNavController(R.id.nav_host_fragment).navigate(R.id.action_mainFragment_to_descriptionFragment, bundle)
    }

}