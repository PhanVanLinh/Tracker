package vn.linh.tracker.feature.main

import android.content.Context

import dagger.Module
import dagger.Provides
import vn.linh.tracker.infrastructure.RequestLocationProvider
import vn.linh.tracker.infrastructure.RequestPermissionProvider

@Module(includes = [MainFragmentBuilderModule::class])
class MainModule {

    @Provides
    internal fun provideRequestPermissionHandler(activity: MainActivity): RequestPermissionProvider {
        return RequestPermissionProvider(activity)
    }

    @Provides
    internal fun provideRequestLocationProvider(activity: MainActivity, context: Context): RequestLocationProvider {
        return RequestLocationProvider(activity, context)
    }
}
