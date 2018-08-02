package vn.linh.domain.interactor.input

import io.reactivex.Completable
import io.reactivex.observers.DisposableCompletableObserver
import vn.linh.domain.scheduler.SchedulerProvider

abstract class CompleteableUseCase<in I : UseCase.Input>(schedulerProvider: SchedulerProvider) : UseCase(schedulerProvider) {

    abstract fun buildUseCaseObservable(input: I): Completable

    fun execute(input: I, observer: DisposableCompletableObserver) {
        val observable = this.buildUseCaseObservable(input)
                .subscribeOn(getSubscribeOnScheduler())
                .observeOn(getObserveOnScheduler())
        subscribe(observable.subscribeWith(observer))
    }
}