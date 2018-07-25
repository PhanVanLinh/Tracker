package vn.linh.data

import dagger.Module
import dagger.Provides
import vn.linh.data.repository.WeatherRepositoryImpl
import vn.linh.data.repository.local.RepoLocalDatasource
import vn.linh.data.repository.remote.api.RepoRemoteDataSource
import vn.linh.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepo(remoteDataSource: RepoRemoteDataSource,
                           localDataSource: RepoLocalDatasource): WeatherRepository {
        return WeatherRepositoryImpl(localDataSource, remoteDataSource)
    }
}