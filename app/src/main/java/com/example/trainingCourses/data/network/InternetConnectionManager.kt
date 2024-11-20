package com.example.trainingCourses.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object InternetConnectionManager {

    private lateinit var connectivityManager: ConnectivityManager

    fun initialize(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun isNetworkAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}