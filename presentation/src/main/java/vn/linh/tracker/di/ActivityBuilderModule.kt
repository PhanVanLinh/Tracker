package com.hieupham.absolutecleanarchitecturekt.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import vn.linh.tracker.feature.main.MainActivity
import vn.linh.tracker.feature.main.MainModule
import vn.linh.tracker.feature.splash.SplashActivity
import vn.linh.tracker.feature.splash.SplashModule

/**
 * The [Module] class declares how to inject an Activity instance into corresponding
 * {@link Module}
 */
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [SplashModule::class])
    @ActivityScope
    internal abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [MainModule::class])
    @ActivityScope
    internal abstract fun bindMainActivity(): MainActivity
}