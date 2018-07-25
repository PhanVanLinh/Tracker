package vn.linh.domain.usecase

import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

abstract class Observer<T> {

    private val ON_SUCCESS = Consumer<T> { this.onSuccess(it) }

    private val ON_ERROR = Consumer<Throwable> { this.onError(it) }

    private val ON_SUBSCRIBE = Consumer<Any> { this.onSubscribed() }

    private val ON_COMPLETE = Action { this.onCompleted() }

    internal fun onSuccess(): Consumer<T> {
        return ON_SUCCESS
    }

    internal fun onError(): Consumer<in Throwable> {
        return ON_ERROR
    }

    internal fun onComplete(): Action {
        return ON_COMPLETE
    }

    internal fun onSubscribe(): Consumer<Any> {
        return ON_SUBSCRIBE
    }

    open fun onSuccess(data: T) {}

    open fun onError(throwable: Throwable) {}

    open fun onCompleted() {}

    open fun onSubscribed() {}
}