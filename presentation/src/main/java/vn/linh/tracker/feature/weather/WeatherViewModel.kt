package vn.linh.tracker.feature.weather

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.observers.DisposableSingleObserver
import vn.linh.domain.entity.Weather
import vn.linh.domain.interactor.GetWeatherUseCase
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

        getWeatherUseCase.execute(input, object : DisposableSingleObserver<Weather>() {
            override fun onSuccess(weather: Weather) {
                weatherViewModelMapper.mapToPresentation(weather)
            }

            override fun onError(e: Throwable) {
            }
        })
    }

    fun stop() {
        getWeatherUseCase.dispose()
    }
}