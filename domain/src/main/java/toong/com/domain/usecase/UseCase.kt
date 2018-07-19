package toong.com.domain.usecase

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import toong.com.domain.usecase.input.Input

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