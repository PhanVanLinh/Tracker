package vn.linh.domain.usecase

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import vn.linh.domain.usecase.input.Input

abstract class UseCase<in I : Input, out O> {

    private val compositeDisposable = CompositeDisposable()

    protected abstract fun buildDataStream(input: I): O

    internal fun subscribe(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun dispose() {
        compositeDisposable.clear()
    }
}