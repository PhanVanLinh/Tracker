package vn.linh.domain.usecase

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.ObjectHelper
import io.reactivex.internal.observers.ConsumerSingleObserver
import vn.linh.domain.entity.Weather
import vn.linh.domain.repository.WeatherRepository
import javax.inject.Inject

open class GetWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) : SingleUseCase<GetWeatherUseCase.Input, Weather>() {

    override fun buildDataStream(input: Input): Single<Weather> {
        return weatherRepository.getWeather(input.latitude, input.longitude)
    }

    data class Input(var latitude: Float, var longitude: Float) : UseCase.Input()

}