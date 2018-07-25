package vn.linh.data.repository.remote.api.service

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.linh.data.BuildConfig
import java.util.concurrent.TimeUnit

class ServiceGenerator {

    companion object {

        private const val CONNECTION_TIMEOUT = 15L

        fun <T> createService(endPoint: String, serviceClass: Class<T>, gson: Gson,
                              interceptor: Interceptor?): T {
            val httpClientBuilder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                httpClientBuilder.addInterceptor(loggingInterceptor)
            }
            if (interceptor != null) {
                httpClientBuilder.addInterceptor(interceptor)
            }
            httpClientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            httpClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            val builder = Retrofit.Builder().baseUrl(endPoint)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            val retrofit = builder.client(httpClientBuilder.build())
                    .build()
            return retrofit.create(serviceClass)
        }
    }
}