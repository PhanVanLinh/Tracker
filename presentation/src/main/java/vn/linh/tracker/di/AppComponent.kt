package vn.linh.tracker.di

import android.app.Application
import com.hieupham.absolutecleanarchitecturekt.di.ActivityBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import vn.linh.tracker.CleanArchitectureApp
import vn.linh.data.NetworkModule
import vn.linh.data.RepositoryModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilderModule::class,
    RepositoryModule::class, NetworkModule::class])
interface AppComponent : AndroidInjector<CleanArchitectureApp> {

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}