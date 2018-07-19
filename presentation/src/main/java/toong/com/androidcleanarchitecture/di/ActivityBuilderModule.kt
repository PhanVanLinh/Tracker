package com.hieupham.absolutecleanarchitecturekt.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import toong.com.androidcleanarchitecture.feature.splash.SplashActivity
import toong.com.androidcleanarchitecture.feature.splash.SplashModule

/**
 * The [Module] class declares how to inject an Activity instance into corresponding
 * {@link Module}
 */
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [SplashModule::class])
    @ActivityScope
    internal abstract fun bindSplashActivity(): SplashActivity
}