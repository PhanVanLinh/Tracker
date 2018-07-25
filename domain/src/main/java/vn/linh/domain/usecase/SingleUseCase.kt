package vn.linh.domain.usecase

import io.reactivex.Scheduler
import io.reactivex.Single

abstract class SingleUseCase<I : UseCase.Input, O> : UseCase<I, Single<O>>() {

    fun execute(input: I, output: Observer<O>, subscribeScheduler: Scheduler, observeScheduler: Scheduler) {
        subscribe(buildDataStream(input).subscribeOn(subscribeScheduler).observeOn(observeScheduler).doOnSubscribe(output.onSubscribe())
                .subscribe(output.onSuccess(), output.onError()))
    }
}