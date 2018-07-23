package vn.linh.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import vn.linh.data.repository.remote.api.middleware.BasicAuthInterceptor
import vn.linh.data.repository.remote.api.service.CleanArchitectureApi
import vn.linh.data.repository.remote.api.service.ServiceGenerator
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        const val API_ENDPOINT = "https://api.github.com/"
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
    fun provideCleanArchitectureApi(gson: Gson, interceptor: BasicAuthInterceptor): CleanArchitectureApi {
        return ServiceGenerator.createService(API_ENDPOINT, CleanArchitectureApi::class.java, gson,
                interceptor)
    }
}