package vn.linh.tracker.feature.main;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import vn.linh.tracker.infrastructure.RequestLocationProvider;
import vn.linh.tracker.infrastructure.RequestPermissionProvider;

@Module
public class MainModule {

    @Provides
    RequestPermissionProvider provideRequestPermissionHandler(MainActivity activity) {
        return new RequestPermissionProvider(activity);
    }

    @Provides
    RequestLocationProvider requestLocationProvider(MainActivity activity, Context context) {
        return new RequestLocationProvider(activity, context);
    }
}
