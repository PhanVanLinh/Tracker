package vn.linh.domain.interactor.input

import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import vn.linh.domain.scheduler.SchedulerProvider

abstract class SingleUseCase<in I : UseCase.Input, O>(schedulerProvider: SchedulerProvider) : UseCase(schedulerProvider) {

    abstract fun buildUseCaseObservable(input: I): Single<O>

    fun execute(input: I, observer: DisposableSingleObserver<O>) {
        val observable = this.buildUseCaseObservable(input)
                .subscribeOn(getSubscribeOnScheduler())
                .observeOn(getObserveOnScheduler())
        subscribe(observable.subscribeWith(observer))
    }
}