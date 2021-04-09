package com.example.transflylocal

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {

        @JvmStatic
        private var retrofitUser: Retrofit? = null

        @JvmStatic
        fun getRetrofitClient(): Retrofit? {
            if (retrofitUser != null) {
                return retrofitUser
            }

            val client = OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).retryOnConnectionFailure(true)
                    .build()

            retrofitUser = Retrofit.Builder()
                    .baseUrl("https://transflyhome.club")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofitUser
        }

    }
}