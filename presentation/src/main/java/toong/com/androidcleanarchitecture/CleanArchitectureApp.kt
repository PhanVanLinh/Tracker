package toong.com.androidcleanarchitecture

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import toong.com.androidcleanarchitecture.di.DaggerAppComponent

class CleanArchitectureApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}