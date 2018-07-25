package vn.linh.tracker.feature.weather

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import vn.linh.domain.entity.Weather
import vn.linh.domain.usecase.GetWeatherUseCase
import vn.linh.domain.usecase.Observer
import vn.linh.tracker.model.WeatherModel
import vn.linh.tracker.model.WeatherViewModelMapper
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
        private val getWeatherUseCase: GetWeatherUseCase,
        private val weatherViewModelMapper: WeatherViewModelMapper
) : ViewModel() {

    val weather: MutableLiveData<WeatherModel> = MutableLiveData()

    fun getWeather() {
        val input = GetWeatherUseCase.Input(16.047079f, 108.206230f)

        getWeatherUseCase.execute(input, object : Observer<Weather>() {
            override fun onSuccess(data: Weather) {
                weather.value = weatherViewModelMapper.mapToPresentation(data)
            }

            override fun onError(throwable: Throwable) {

            }
        }, Schedulers.computation(), AndroidSchedulers.mainThread())
    }

    fun stop() {
        getWeatherUseCase.dispose()
    }
}