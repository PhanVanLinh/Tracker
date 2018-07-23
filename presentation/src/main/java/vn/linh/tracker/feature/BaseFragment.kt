package vn.linh.tracker.feature

import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {
    abstract fun onBackPressed(): Boolean
}