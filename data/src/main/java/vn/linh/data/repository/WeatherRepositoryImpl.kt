package vn.linh.data.repository

import io.reactivex.Single
import vn.linh.data.repository.local.RepoLocalDatasource
import vn.linh.data.repository.remote.api.RepoRemoteDataSource
import vn.linh.domain.entity.Weather
import vn.linh.domain.repository.WeatherRepository

class WeatherRepositoryImpl(private val localDataSource: RepoLocalDatasource,
                            private val remoteDataSource: RepoRemoteDataSource
) : WeatherRepository {

    override fun getWeather(latitude: Float, longitude: Float): Single<Weather> {
        return remoteDataSource.getWeather(latitude, longitude).map { it.mapToDomain() }
    }
}