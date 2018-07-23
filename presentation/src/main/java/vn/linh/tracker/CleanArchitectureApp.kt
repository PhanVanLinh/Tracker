package vn.linh.tracker

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import vn.linh.tracker.di.DaggerAppComponent

class CleanArchitectureApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}