package vn.linh.data.repository.remote.api

import io.reactivex.Single
import vn.linh.data.model.WeatherData
import vn.linh.data.repository.remote.api.service.WeatherApi
import javax.inject.Inject

class RepoRemoteDataSource @Inject constructor(private val cleanArchitectureApi: WeatherApi) {

    fun getWeather(latitude: Float, longitude: Float): Single<WeatherData> {
        return cleanArchitectureApi.getWeather(latitude, longitude, "45fd130916452ca4402e1371f86647a7")
    }
}