package vn.linh.tracker.model

import vn.linh.domain.entity.Weather
import vn.linh.domain.entity.Wind
import javax.inject.Inject

data class WeatherModel(
        var temp: Float,
        var icon: String,
        var pressure: Float,
        var humidity: Float,
        var tempMin: Float,
        var tempMax: Float,
        var wind: Wind
)

class WeatherViewModelMapper @Inject constructor() {

    fun mapToPresentation(model: Weather): WeatherModel = WeatherModel(
            temp = model.temp,
            icon = model.icon,
            pressure = model.pressure,
            humidity = model.humidity,
            tempMin = model.tempMin,
            tempMax = model.tempMax,
            wind = model.wind
    )
}
