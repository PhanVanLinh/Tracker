package vn.linh.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import vn.linh.data.repository.remote.api.middleware.BasicAuthInterceptor
import vn.linh.data.repository.remote.api.service.WeatherApi
import vn.linh.data.repository.remote.api.service.ServiceGenerator
import javax.inject.Singleton
import kotlin.jvm.java

@Module
class NetworkModule {

    companion object {
        const val API_ENDPOINT = "http://api.openweathermap.org/"
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(context: Context): BasicAuthInterceptor {
        return BasicAuthInterceptor("", "")
    }

    @Singleton
    @Provides
    fun provideCleanArchitectureApi(gson: Gson, interceptor: BasicAuthInterceptor): WeatherApi {
        return ServiceGenerator.createService(API_ENDPOINT, WeatherApi::class.java, gson,
                interceptor)
    }
}