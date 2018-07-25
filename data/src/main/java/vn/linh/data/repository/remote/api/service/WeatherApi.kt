package vn.linh.data.repository.remote.api.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vn.linh.data.model.WeatherData

interface WeatherApi {
    //    http://api.openweathermap.org/data/2.5/weather?lat=16.047079&lon=108.206230&appid=45fd130916452ca4402e1371f86647a7
    @GET("/data/2.5/weather")
    fun getWeather(@Query("lat") latitude: Float, @Query("lon") longitude: Float, @Query("appid") app:String): Single<WeatherData>
}

