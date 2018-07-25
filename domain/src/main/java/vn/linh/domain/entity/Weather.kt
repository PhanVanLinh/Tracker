package vn.linh.domain.entity

data class Weather(
        var temp: Float,
        var icon: String,
        var pressure: Float,
        var humidity: Float,
        var tempMin: Float,
        var tempMax: Float,
        var wind: Wind
)