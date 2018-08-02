package vn.linh.domain.repository

import io.reactivex.Single
import vn.linh.domain.entity.Weather

interface WeatherRepository : Repository {
    fun getWeather(latitude: Float, longitude: Float): Single<Weather>

    fun getWeather(): Single<Weather>
}