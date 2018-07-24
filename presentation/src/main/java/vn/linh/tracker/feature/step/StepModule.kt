package vn.linh.tracker.feature.step

import android.content.Context
import dagger.Module
import dagger.Provides
import vn.linh.tracker.infrastructure.StepSensorProvider

@Module
class StepModule {

    @Provides
    internal fun provideStepSensor(fragment: StepFragment, context: Context): StepSensorProvider {
        return StepSensorProvider(fragment, context)
    }
}