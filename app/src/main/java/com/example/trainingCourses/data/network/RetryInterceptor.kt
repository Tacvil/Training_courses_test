package com.example.trainingCourses.data.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.math.pow

class RetryInterceptor(private val maxRetries: Int = 3) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response: Response? = null
        var tryCount = 0

        while (response == null && tryCount < maxRetries) {
            try {
                response = chain.proceed(request)
            } catch (e: SocketTimeoutException) {
                Timber.w(e, "Request timed out, retrying...")
                tryCount++
                if (tryCount < maxRetries) {
                    Thread.sleep((2.0.pow(tryCount) * 1000).toLong())
                }
            }
        }

        return response ?: throw IOException("Request failed after $maxRetries retries")
    }
}