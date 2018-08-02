package vn.linh.domain.interactor.input

import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import vn.linh.domain.scheduler.SchedulerProvider

abstract class ObserverbleUseCase<in I : UseCase.Input, O>(schedulerProvider: SchedulerProvider) : UseCase(schedulerProvider) {

    abstract fun buildUseCaseObservable(input: I): Observable<O>

    fun execute(input: I, observer: DisposableObserver<O>) {
        val observable = this.buildUseCaseObservable(input)
                .subscribeOn(getSubscribeOnScheduler())
                .observeOn(getObserveOnScheduler())
        subscribe(observable.subscribeWith(observer))
    }
}