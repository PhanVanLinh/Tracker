package vn.linh.tracker.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import vn.linh.tracker.feature.weather.WeatherViewModel
import vn.linh.tracker.viewmodel.TrackerViewModelFactory

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(
            providerFactory: TrackerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    internal abstract fun bindUserViewModel(userViewModel: WeatherViewModel): ViewModel
}
