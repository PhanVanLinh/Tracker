package vn.linh.domain.interactor

import io.reactivex.Single
import vn.linh.domain.entity.Weather
import vn.linh.domain.repository.WeatherRepository
import vn.linh.domain.scheduler.SchedulerProvider
import vn.linh.domain.interactor.input.SingleUseCase
import vn.linh.domain.interactor.input.UseCase
import javax.inject.Inject

internal class GetWeatherListUseCase @Inject constructor(scheduler: SchedulerProvider, private val weatherRepository: WeatherRepository)
    : SingleUseCase<UseCase.EmptyInput, Weather>(scheduler) {

    override fun buildUseCaseObservable(input: EmptyInput): Single<Weather> {
        return weatherRepository.getWeather()
    }
}