package vn.linh.data.model

import com.google.gson.annotations.SerializedName
import vn.linh.domain.entity.Weather
import vn.linh.domain.entity.Wind

class WeatherData(
        val temp: Float,
        @SerializedName("weather")
        var weather: List<WWW>,
        var icon: String,
        var pressure: Float,
        var humidity: Float,
        @SerializedName("temp_min")
        var tempMin: Float,
        @SerializedName("temp_max")
        var tempMax: Float,
        var wind: Wind
) {
    fun mapToDomain(): Weather = Weather(
            temp = temp,
            icon = icon,
            pressure = pressure,
            humidity = humidity,
            tempMin = tempMin,
            tempMax = tempMax,
            wind = wind
    )
}