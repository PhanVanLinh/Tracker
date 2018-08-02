package vn.linh.tracker.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import vn.linh.domain.scheduler.SchedulerProvider
import vn.linh.tracker.AppSchedulerProvider
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }
}
