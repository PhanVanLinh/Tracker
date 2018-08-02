package vn.linh.domain.interactor

import io.reactivex.Single
import vn.linh.domain.entity.Weather
import vn.linh.domain.interactor.input.SingleUseCase
import vn.linh.domain.interactor.input.UseCase
import vn.linh.domain.repository.WeatherRepository
import vn.linh.domain.scheduler.SchedulerProvider
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(scheduler: SchedulerProvider, private val weatherRepository: WeatherRepository)
    : SingleUseCase<GetWeatherUseCase.Input, Weather>(scheduler) {

    override fun buildUseCaseObservable(input: Input): Single<Weather> {
        return weatherRepository.getWeather(input.latitude, input.longitude)
    }

    data class Input(var latitude: Float, var longitude: Float) : UseCase.Input()
}