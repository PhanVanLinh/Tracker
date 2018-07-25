package vn.linh.tracker.feature.step

import android.content.Context
import dagger.Module
import dagger.Provides
import vn.linh.tracker.infrastructure.fit.FitProvider
import vn.linh.tracker.infrastructure.fit.GoogleFitProvider

@Module
class StepModule {

    @Provides
    internal fun provideFitProvider(fragment: StepFragment, context: Context): FitProvider {
        return GoogleFitProvider(fragment, context)
    }
}