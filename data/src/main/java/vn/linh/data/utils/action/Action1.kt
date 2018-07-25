package vn.linh.data.utils.action

interface Action1<T> : Action {

    fun call(t: T)

}