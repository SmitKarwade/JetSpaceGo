package com.example.jetspacego.gcp

import android.content.Context
import com.google.auth.oauth2.GoogleCredentials
import okhttp3.Interceptor
import okhttp3.Response
import java.io.InputStream

class AuthInterceptor(context: Context) : Interceptor {

    private val credentials: GoogleCredentials

    init {
        val assetManager = context.assets
        val inputStream: InputStream = assetManager.open("cohesive-bolt-451917-r0-e6c6770ce437.json")
        credentials = GoogleCredentials.fromStream(inputStream)
            .createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        credentials.refreshIfExpired()
        val token = credentials.accessToken.tokenValue

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}
