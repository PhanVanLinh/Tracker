package vn.linh.tracker.feature.main

import com.hieupham.absolutecleanarchitecturekt.di.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector
import vn.linh.tracker.feature.other.OtherFragment
import vn.linh.tracker.feature.other.OtherModule
import vn.linh.tracker.feature.step.StepFragment
import vn.linh.tracker.feature.step.StepModule
import vn.linh.tracker.feature.weather.WeatherFragment
import vn.linh.tracker.feature.weather.WeatherModule

@Module
abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector()
    @FragmentScope
    internal abstract fun bindContainerFragment(): ContainerFragment

    @ContributesAndroidInjector(modules = [StepModule::class])
    @FragmentScope
    internal abstract fun bindStepFragment(): StepFragment

    @ContributesAndroidInjector(modules = [WeatherModule::class])
    @FragmentScope
    internal abstract fun bindWeatherFragment(): WeatherFragment

    @ContributesAndroidInjector(modules = [OtherModule::class])
    @FragmentScope
    internal abstract fun bindOtherFragment(): OtherFragment
}